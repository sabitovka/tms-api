package io.sabitovka.tms.api.service;

import io.sabitovka.tms.api.auth.CustomUserDetails;
import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.enums.UserRole;

/**
 * Сервис регистрации и авторизации пользователей в системе
 */
public interface AuthService {
    /**
     * Выполняет регистрацию в системе
     * @param registerUserDto Регистрационные данные
     */
    void register(RegisterUserDto registerUserDto);

    /**
     * Выполняет логин в системе
     * @param loginDto Данные для входа
     * @return Токен авторизации
     */
    String login(LoginDto loginDto);

    /**
     * Возвращает текущего пользователя из контекста авторизации
     * @return Данные авторизованного пользователя
     */
    CustomUserDetails getCurrentUser();

    /**
     * Проверяет, есть ли у авторизованного пользователя роль
     * @param userRole Роль, которую нужно проверить на наличие
     * @return `true` при наличии роли
     */
    boolean hasAuthority(UserRole userRole);
}
