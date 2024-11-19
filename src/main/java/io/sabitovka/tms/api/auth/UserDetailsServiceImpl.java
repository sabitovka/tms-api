package io.sabitovka.tms.api.auth;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.entity.User;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserDetailsService} для поиска пользователя в БД
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException(ErrorCode.CONFLICT, Constants.TOKEN_IS_VALID_BUT_USER_NOT_FOUND_TEXT));

        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getId(), user.getRole().toString());
    }
}
