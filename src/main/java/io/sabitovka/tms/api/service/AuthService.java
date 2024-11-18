package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;

/**
 * Сервис регистрации и авторизации пользователей в системе
 */
public interface AuthService {
    void register(RegisterUserDto registerUserDto);
    String login(LoginDto loginDto);
}
