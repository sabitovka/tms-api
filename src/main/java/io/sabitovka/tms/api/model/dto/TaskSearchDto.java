package io.sabitovka.tms.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskSearchDto extends PagingAndSortingDto {
    private String title; // Фильтр по названию задачи
    private String status; // Фильтр по статусу задачи
    private String priority; // Фильтр по приоритету задачи
    private String assignee; // Фильтр по имени исполнителя
    private String author; // Фильтр по имени автора
}
