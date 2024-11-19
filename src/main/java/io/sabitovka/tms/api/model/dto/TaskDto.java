package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.dto.UserInfoDto;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.model.enums.TaskPriority;
import io.sabitovka.tms.api.model.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Task}
 */
public record TaskDto(Long id, @NotBlank String title, String description, TaskStatus status,
                            TaskPriority priority, @NotNull UserInfoDto author,
                            @NotNull UserInfoDto performer) implements Serializable {
}
