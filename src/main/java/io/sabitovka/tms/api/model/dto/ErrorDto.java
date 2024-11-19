package io.sabitovka.tms.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO для вывода ошибки
 */
@Getter
@Setter
public class ErrorDto {
    private int internalCode;
    private int status;
    private String error;
    private String message;
    private boolean success = false;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
}
