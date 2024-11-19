package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.auth.CustomUserDetails;
import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.entity.User;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.model.enums.UserRole;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.service.AuthService;
import io.sabitovka.tms.api.util.Constants;
import io.sabitovka.tms.api.util.JwtTokenProvider;
import io.sabitovka.tms.api.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.email())) {
            throw new ApplicationException(ErrorCode.BAD_REQUEST, Constants.USER_ALREADY_EXISTS_TEXT);
        }

        User user = userMapper.toEntity(registerUserDto);
        String hashedPassword = passwordEncoder.encode(registerUserDto.password());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Override
    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.username())
                .orElseThrow(() -> new ApplicationException(ErrorCode.UNAUTHORIZED, Constants.LOGIN_OR_PASSWORD_INCORRECT_TEXT));

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED, Constants.LOGIN_OR_PASSWORD_INCORRECT_TEXT);
        }

        return jwtTokenProvider.createToken(user.getEmail());
    }

    @Override
    public CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean hasAuthority(UserRole userRole) {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(userRole.toString()));
    }
}
