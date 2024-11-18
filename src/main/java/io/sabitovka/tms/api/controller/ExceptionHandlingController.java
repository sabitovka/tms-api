package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.ErrorDto;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDto> handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpCode())
                .body(wrapToErrorDto(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(wrapToErrorDto(new ApplicationException(ErrorCode.INTERNAL_ERROR, e.getMessage())));
    }

    private ErrorDto wrapToErrorDto(ApplicationException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setInternalCode(exception.getErrorCode().getId());
        errorDto.setStatus(exception.getErrorCode().getHttpCode());
        errorDto.setError(exception.getErrorCode().getMessage());
        errorDto.setMessage(exception.getMessage());
        return errorDto;
    }
}
