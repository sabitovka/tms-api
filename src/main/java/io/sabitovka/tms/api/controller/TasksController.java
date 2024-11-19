package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.service.impl.TaskServiceImpl;
import io.sabitovka.tms.api.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessDto<TaskDto>> create(@RequestBody TaskDto taskDto) {
        TaskDto created = taskService.create(taskDto);
        return ResponseWrapper.wrap(created).withStatus(HttpStatus.CREATED).toResponse();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> deleteById(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> update(@PathVariable("id") Long id, @RequestBody TaskDto taskDto) {
        taskService.updateById(id, taskDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> partialUpdate(@PathVariable("id") Long id, @RequestBody TaskDto taskDto) {
        taskService.updateById(id, taskDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SuccessDto<Void>> changeStatus(@PathVariable("id") Long id, @RequestBody StatusDto statusDto) {
        taskService.changeStatus(id, statusDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }
}
