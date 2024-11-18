package io.sabitovka.tms.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String LOGIN_OR_PASSWORD_INCORRECT_TEXT = "Неверный логин или пароль";
    public static final String USER_ALREADY_EXISTS_TEXT = "Пользователь с таким email уже зарегистрирован";
    public static final String TOKEN_IS_VALID_BUT_USER_NOT_FOUND_TEXT = "Не удалось найти пользователя в БД по переданному токену";
}
