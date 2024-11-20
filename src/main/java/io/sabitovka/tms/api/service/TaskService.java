package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.model.dto.StatusDto;
import io.sabitovka.tms.api.model.dto.TaskDto;
import io.sabitovka.tms.api.model.dto.TaskSearchDto;
import org.springframework.data.domain.Page;

public interface TaskService {
    Page<TaskDto> getAllTasks(TaskSearchDto searchDto);
    TaskDto getById(Long id);
    TaskDto create(TaskDto taskDto);

    void deleteById(Long id);

    void updateById(Long id, TaskDto taskDto);

    void changeStatus(Long id, StatusDto statusDto);

    void findAllCommentsByTaskId(Long id);
}
