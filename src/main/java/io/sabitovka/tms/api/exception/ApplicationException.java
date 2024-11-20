package io.sabitovka.tms.api.exception;

import io.sabitovka.tms.api.model.enums.ErrorCode;
import lombok.Getter;

/**
 * Класс общего исключения для проекта
 */
@Getter
public class ApplicationException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public ApplicationException(ErrorCode errorCode, String details) {
        super(details);
        this.errorCode = errorCode;
    }

    public ApplicationException(ErrorCode errorCode, Throwable throwable) {
        this(errorCode, throwable.getMessage());
    }
}
