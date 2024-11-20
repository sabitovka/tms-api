package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.enums.TaskPriority;
import io.sabitovka.tms.api.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для выполнения поиска, пагинации и сортировки задач
 */
@Getter
@Setter
public class TaskSearchDto extends PagingAndSortingDto {
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private Long authorId;
    private Long performerId;
}
