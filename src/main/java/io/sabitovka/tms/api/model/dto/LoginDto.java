package io.sabitovka.tms.api.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для входа в систем
 * @param username Имя пользователя
 * @param password Пароль
 */
public record LoginDto(
        @NotBlank String username,
        @NotBlank String password
) {}
