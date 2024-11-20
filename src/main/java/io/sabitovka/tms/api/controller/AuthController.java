package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.ErrorDto;
import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.service.AuthService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Авторизация", description = "Сервис для авторизации")
@SecurityRequirement(name = Constants.BEARER_AUTHORIZATION)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Зарегистрировать нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Созданная привычка"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<SuccessDto<Void>> register(@RequestBody RegisterUserDto createUserDto) {
        authService.register(createUserDto);
        return ResponseWrapper.noContent().withStatus(HttpStatus.CREATED).toResponse();
    }

    @Operation(
            summary = "Выполнить вход в систему",
            description = "Вход в систему подразумевает получение токена авторизации. Токен авторизации содержит подпись для email и claims с userId и role")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Созданная привычка"),
            @ApiResponse(description = "Любая ошибка", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessDto<String>> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseWrapper.wrap(token).toResponse();
    }
}
