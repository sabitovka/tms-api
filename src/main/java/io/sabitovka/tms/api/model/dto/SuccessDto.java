package io.sabitovka.tms.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * DTO успешного ответа, позволяющего обернуть результат
 */
@Getter
public class SuccessDto<T> {
    private final int status;
    private final T data;
    private final boolean success = true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public SuccessDto(T data) {
        this(HttpStatus.OK, data);
    }

    public SuccessDto(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }
}
