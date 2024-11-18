package io.sabitovka.tms.api.controller;

import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.dto.SuccessDto;
import io.sabitovka.tms.api.service.AuthService;
import io.sabitovka.tms.api.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static io.sabitovka.tms.api.util.ResponseWrapper.wrap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SuccessDto<Void>> register(@RequestBody RegisterUserDto createUserDto) {
        authService.register(createUserDto);
        return ResponseWrapper.wrap().withStatus(HttpStatus.CREATED).toResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessDto<String>> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return wrap(token).toResponse();
    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
