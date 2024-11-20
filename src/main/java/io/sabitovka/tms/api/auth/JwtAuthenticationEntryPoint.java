package io.sabitovka.tms.api.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.ErrorDto;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.util.Wrappers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Компонент предоставляет точку входа для обработки ошибок авторизации.
 * Позволяет обернуть ошибки авторизации в {@link ErrorDto} и вывести конкретные ошибки исключений
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        String message = throwable != null ?
                throwable.getMessage() :
                authException.getMessage();

        ErrorDto errorDto = Wrappers.wrapToErrorDto(new ApplicationException(ErrorCode.UNAUTHORIZED, message));
        response.getWriter().write(objectMapper.writeValueAsString(errorDto));
    }
}
