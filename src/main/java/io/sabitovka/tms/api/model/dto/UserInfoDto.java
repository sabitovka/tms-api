package io.sabitovka.tms.api.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import io.sabitovka.tms.api.model.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserInfoDto(
        Long id,

        @NotNull
        @NotEmpty
        @NotBlank
        String username,

        @NotNull
        @Email
        @NotEmpty
        @NotBlank
        String email
) implements Serializable {}
