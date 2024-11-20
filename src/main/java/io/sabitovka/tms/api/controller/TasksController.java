package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.*;
import io.sabitovka.tms.api.service.TaskService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Задачи")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = Constants.BEARER_AUTHORIZATION)
public class TasksController {
    private final TaskService taskService;

    @Operation(summary = "Получить список задач. Доступно только админу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список задач"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<Page<TaskDto>>> findAll(TaskSearchDto taskSearchDto) {
        Page<TaskDto> allTasks = taskService.getAllTasks(taskSearchDto);
        return ResponseWrapper.wrap(allTasks).toResponse();
    }

    @Operation(summary = "Получить задачу по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Найденная задача"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessDto<TaskDto>> findById(@PathVariable("id") Long id) {
        TaskDto task = taskService.getById(id);
        return ResponseWrapper.wrap(task).toResponse();
    }

    @Operation(summary = "Создать задачу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Созданная задача"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<TaskDto>> create(@RequestBody TaskDto taskDto) {
        TaskDto created = taskService.create(taskDto);
        return ResponseWrapper.wrap(created).withStatus(HttpStatus.CREATED).toResponse();
    }

    @Operation(summary = "Удалить задачу по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача удалена"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> deleteById(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @Operation(summary = "Обновить задачу полностью")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача обновлена"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> update(@PathVariable("id") Long id, @RequestBody TaskDto taskDto) {
        taskService.updateById(id, taskDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @Operation(summary = "Обновить задачу полностью частично")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Задача обновлена"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessDto<Void>> partialUpdate(@PathVariable("id") Long id, @RequestBody TaskDto taskDto) {
        taskService.updateById(id, taskDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }

    @Operation(summary = "Изменить статус задачи")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Статус изменен"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<SuccessDto<Void>> changeStatus(@PathVariable("id") Long id, @RequestBody StatusDto statusDto) {
        taskService.changeStatus(id, statusDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.NO_CONTENT).toResponse();
    }
}
