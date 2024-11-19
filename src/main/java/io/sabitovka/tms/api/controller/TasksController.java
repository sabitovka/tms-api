package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.service.impl.TaskServiceImpl;
import io.sabitovka.tms.api.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TasksController {
    private final TaskServiceImpl taskService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<Page<TaskDto>>> findAll(TaskSearchDto taskSearchDto) {
        Page<TaskDto> allTasks = taskService.getAllTasks(taskSearchDto);
        return ResponseWrapper.wrap(allTasks).toResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDto<TaskDto>> findById(@PathVariable("id") Long id) {
        TaskDto task = taskService.getById(id);
        return ResponseWrapper.wrap(task).toResponse();
    }

}
