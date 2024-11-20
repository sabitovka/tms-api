package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.auth.Insurance;
import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.repository.TaskRepository;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.specification.TaskSpecification;
import io.sabitovka.tms.api.util.mapper.TaskMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskSpecification taskSpecification;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private Insurance insurance;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("[getAllTasks] Успешное получение задач с пагинацией и сортировкой")
    void getAllTasksShouldReturnPaginatedTasks() {
        TaskSearchDto searchDto = new TaskSearchDto();
        searchDto.setAuthorId(1L);
        searchDto.setSortBy("title");
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, searchDto.getSortBy()));
        Specification<Task> specification = mock(Specification.class);

        List<Task> taskList = List.of(new Task(), new Task());
        Page<Task> taskPage = new PageImpl<>(taskList, pageable, taskList.size());

        when(taskSpecification.build(searchDto)).thenReturn(specification);
        when(taskRepository.findAll(specification, pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(any(Task.class))).thenReturn(any(TaskDto.class));

        Page<TaskDto> result = taskService.getAllTasks(searchDto);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        verify(taskRepository).findAll(specification, pageable);
        verify(taskMapper, times(2)).toDto(any(Task.class));
    }

    @Test
    @DisplayName("[getById] Успешное получение задачи по ID")
    void getByIdShouldReturnTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(new TaskDto(
                taskId, "", null, null, null, null, null, null, null)
        );

        TaskDto result = taskService.getById(taskId);

        assertThat(result).isNotNull();
        verify(insurance).ensureHasAccessToTheTask(task);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).toDto(task);
    }

    @Test
    @DisplayName("[getById] Если задача не найдена, выбрасывает исключение")
    void getByIdWhenTaskNotFoundShouldThrowException() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getById(taskId))
                .isInstanceOf(ApplicationException.class);

        verify(taskRepository).findById(taskId);
        verifyNoInteractions(taskMapper, insurance);
    }

    @Test
    @DisplayName("[create] Успешное создание задачи")
    void createShouldSaveAndReturnTask() {
        TaskDto taskDto = mock(TaskDto.class);
        Task task = new Task();
        Task savedTask = new Task();
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.saveAndFlush(task)).thenReturn(savedTask);
        when(taskMapper.toDto(savedTask)).thenReturn(new TaskDto(
                1L, "", null, null, null, 1L, 2L, null, null));
        when(userRepository.existsById(anyLong())).thenReturn(true);

        TaskDto result = taskService.create(taskDto);

        assertThat(result).isNotNull();
        verify(userRepository, times(2)).existsById(anyLong());
        verify(taskRepository).saveAndFlush(task);
        verify(taskMapper).toDto(savedTask);
    }

    @Test
    @DisplayName("[create] Если пользователь не существует, выбрасывает исключение")
    void createWhenUserNotFoundShouldThrowException() {
        TaskDto taskDto = mock(TaskDto.class);
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> taskService.create(taskDto))
                .isInstanceOf(ApplicationException.class);
        verify(taskRepository, times(0)).saveAndFlush(any());
        verify(taskMapper, times(0)).toDto(any());
    }

    @Test
    @DisplayName("[deleteById] Успешное удаление задачи")
    void deleteByIdShouldDeleteTask() {
        Long taskId = 1L;

        taskService.deleteById(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    @DisplayName("[updateById] Успешное обновление задачи")
    void updateByIdShouldUpdateTask() {
        Long taskId = 1L;
        TaskDto taskDto = mock(TaskDto.class);
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.updateById(taskId, taskDto);

        verify(taskMapper).partialUpdate(taskDto, task);
        verify(taskRepository).save(task);
    }

    @Test
    @DisplayName("[updateById] Если задача не найдена, выбрасывает исключение")
    void updateByIdWhenTaskNotFoundShouldThrowException() {
        Long taskId = 1L;
        TaskDto taskDto = mock(TaskDto.class);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateById(taskId, taskDto))
                .isInstanceOf(ApplicationException.class);
        verifyNoInteractions(taskMapper);
    }

    @Test
    @DisplayName("[changeStatus] Успешное изменение статуса задачи")
    void changeStatusShouldUpdateStatus() {
        Long taskId = 1L;
        StatusDto statusDto = new StatusDto();
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.changeStatus(taskId, statusDto);

        verify(insurance).ensureHasAccessToTheTask(task);
        verify(taskRepository).save(task);
    }
}
