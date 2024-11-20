package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для обновления статуса
 */
@Getter
@Setter
public class StatusDto {
    TaskStatus status;
}
