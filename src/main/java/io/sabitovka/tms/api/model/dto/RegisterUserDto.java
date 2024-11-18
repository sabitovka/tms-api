package io.sabitovka.tms.api.model.dto;

import io.sabitovka.tms.api.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * DTO для {@link User} при регистрации
 */
public record RegisterUserDto(
    @NotNull(message = "Не указан обязательный параметр username")
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 50)
    String username,
    @NotNull(message = "Не указан обязательный параметр email")
    @Email
    @NotEmpty
    @NotBlank
    String email,

    @NotNull(message = "Не указан обязательный параметр password")
    @NotEmpty
    @NotBlank
    String password
) {}
