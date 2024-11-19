package io.sabitovka.tms.api.model.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank String username,
        @NotBlank String password
) {}
