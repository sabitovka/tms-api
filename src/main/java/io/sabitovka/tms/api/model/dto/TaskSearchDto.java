package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.enums.TaskPriority;
import io.sabitovka.tms.api.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSearchDto extends PagingAndSortingDto {
    private String title; // Фильтр по названию задачи
    private TaskStatus status; // Фильтр по статусу задачи
    private TaskPriority priority; // Фильтр по приоритету задачи
    private Long authorId;
    private Long performerId;
}
