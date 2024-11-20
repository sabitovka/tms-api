package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.ErrorDto;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.util.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingController {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDto> handleApplicationException(ApplicationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpCode())
                .body(Wrappers.wrapToErrorDto(e));
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ErrorDto> handleCredentialsExpiredException(CredentialsExpiredException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Wrappers.wrapToErrorDto(new ApplicationException(ErrorCode.UNAUTHORIZED, e)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Wrappers.wrapToErrorDto(new ApplicationException(ErrorCode.FORBIDDEN, e)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(Wrappers.wrapToErrorDto(new ApplicationException(ErrorCode.INTERNAL_ERROR, e.getMessage())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest()
                .body(Wrappers.wrapToErrorDto(new ApplicationException(ErrorCode.BAD_REQUEST, errors.toString())));
    }
}
