package io.sabitovka.tms.api.auth;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Кастомная информация о пользователе для авторизации.
 * Содержит userId для того, чтобы было проще обращаться к сущностям
 */
public class CustomUserDetails extends User {
    @Getter
    private final Long userId;

    public CustomUserDetails(String username, String password, Long userId, String authorities) {
        super(username, password, Collections.singleton(new SimpleGrantedAuthority(authorities)));
        this.userId = userId;
    }
}
