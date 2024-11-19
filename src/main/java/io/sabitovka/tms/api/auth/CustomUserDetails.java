package io.sabitovka.tms.api.auth;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CustomUserDetails extends User {
    @Getter
    private final Long userId;

    public CustomUserDetails(String username, String password, Long userId, String authorities) {
        super(username, password, Collections.singleton(new SimpleGrantedAuthority(authorities)));
        this.userId = userId;
    }
}
