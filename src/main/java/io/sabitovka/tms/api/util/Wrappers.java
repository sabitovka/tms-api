package io.sabitovka.tms.api.util;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.ErrorDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Wrappers {
    public static ErrorDto wrapToErrorDto(ApplicationException exception) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setInternalCode(exception.getErrorCode().getId());
        errorDto.setStatus(exception.getErrorCode().getHttpCode().value());
        errorDto.setError(exception.getErrorCode().getMessage());
        errorDto.setMessage(exception.getMessage());
        return errorDto;
    }
}
