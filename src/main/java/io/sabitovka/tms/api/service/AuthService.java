package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.auth.CustomUserDetails;
import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.enums.UserRole;

/**
 * Сервис регистрации и авторизации пользователей в системе
 */
public interface AuthService {
    void register(RegisterUserDto registerUserDto);
    String login(LoginDto loginDto);
    CustomUserDetails getCurrentUser();
    boolean hasAuthority(UserRole userRole);
}
