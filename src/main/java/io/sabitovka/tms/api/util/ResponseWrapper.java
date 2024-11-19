package io.sabitovka.tms.api.util;

import io.sabitovka.tms.api.model.dto.SuccessDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseWrapper<T> {
    private final T data;
    private HttpStatus status = HttpStatus.OK;

    public static ResponseWrapper<Void> noContent() {
        return wrap(null);
    }

    public static <T> ResponseWrapper<T> wrap(T data) {
        return new ResponseWrapper<>(data);
    }

    public ResponseWrapper<T> withStatus(HttpStatus httpStatus) {
        this.status = httpStatus;
        return this;
    }

    public ResponseEntity<SuccessDto<T>> toResponse() {
        return ResponseEntity.status(status).body(new SuccessDto<>(status, data));
    }
}
