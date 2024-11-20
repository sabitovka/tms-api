package io.sabitovka.tms.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String LOGIN_OR_PASSWORD_INCORRECT_TEXT = "Неверный логин или пароль";
    public static final String USER_ALREADY_EXISTS_TEXT = "Пользователь с таким email уже зарегистрирован";
    public static final String TOKEN_IS_VALID_BUT_USER_NOT_FOUND_TEXT = "Не удалось найти пользователя в БД по переданному токену";
    public static final String TASK_WITH_THE_ID_NOT_FOUND_TEXT_F = "Задача с id = %s не найдена";
    public static final String ACCESS_DENIED_TO_THE_TASK = "У вас нет доступа к этой задаче";
    public static final String BEARER_AUTHORIZATION = "Bearer";
    public static final String USER_WITH_THE_ID_NOT_FOUND_TEXT_F = "Пользователь с id = %d не найден";
}
