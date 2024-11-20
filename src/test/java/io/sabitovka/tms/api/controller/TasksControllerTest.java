package io.sabitovka.tms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.repository.TaskRepository;
import io.sabitovka.tms.api.service.TaskService;
import io.sabitovka.tms.api.util.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TasksController.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private TaskRepository taskRepository;

    private static final String BASE_URL = "/api/tasks";

    @Test
    @DisplayName("[findAll] Админ успешно получает список задач")
    @WithMockUser(authorities = "ADMIN")
    void findAllShouldReturnTaskList() throws Exception {
        Page<TaskDto> taskPage = new PageImpl<>(List.of(
                new TaskDto(1L, "", null, null, null, 1L, null, null, null),
                new TaskDto(2L, "", null, null, null, 1L, null, null, null)
        ));
        when(taskService.getAllTasks(any(TaskSearchDto.class))).thenReturn(taskPage);

        mockMvc.perform(get(BASE_URL)
                        .param("page", "0")
                        .param("size", "10")
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content", hasSize(2)))
                .andExpect(jsonPath("$.data.content[0]").exists())
                .andExpect(jsonPath("$.data.content[1]").exists());

        verify(taskService).getAllTasks(any(TaskSearchDto.class));
    }

    @Test
    @DisplayName("[findById] Успешное получение задачи по ID")
    @WithMockUser(authorities = "ADMIN")
    void findByIdShouldReturnTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "", null, null, null, 1L, 2L, null, null);
        when(taskService.getById(1L)).thenReturn(taskDto);

        mockMvc.perform(get(BASE_URL + "/{id}", 1L)
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());

        verify(taskService).getById(1L);
    }

    @Test
    @DisplayName("[create] Админ успешно создаёт задачу")
    @WithMockUser(authorities = "ADMIN")
    void createShouldReturnCreatedTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "", null, null, null, null, null, null, null);
        when(taskService.create(any(TaskDto.class))).thenReturn(taskDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto))
                        .with(csrf().asHeader()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").exists());

        verify(taskService).create(any(TaskDto.class));
    }

    @Test
    @DisplayName("[deleteById] Админ успешно удаляет задачу")
    @WithMockUser(authorities = "ADMIN")
    void deleteByIdShouldDeleteTask() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/{id}", 1L)
                        .with(csrf().asHeader()))
                .andExpect(status().isNoContent());

        verify(taskService).deleteById(1L);
    }

    @Test
    @DisplayName("[update] Админ успешно обновляет задачу")
    @WithMockUser(authorities = "ADMIN")
    void updateShouldUpdateTask() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "", null, null, null, null, null, null, null);

        mockMvc.perform(put(BASE_URL + "/{id}", 1L)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isNoContent());

        verify(taskService).updateById(eq(1L), any(TaskDto.class));
    }

    @Test
    @DisplayName("[changeStatus] Успешное обновление статуса задачи")
    @WithMockUser()
    void changeStatusShouldUpdateTaskStatus() throws Exception {
        StatusDto statusDto = new StatusDto();

        mockMvc.perform(put(BASE_URL + "/{id}/status", 1L)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isNoContent());

        verify(taskService).changeStatus(eq(1L), any(StatusDto.class));
    }
}

