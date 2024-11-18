package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.entity.User;
import io.sabitovka.tms.api.model.enums.UserRole;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.util.JwtTokenProvider;
import io.sabitovka.tms.api.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.email())) {
            throw new IllegalArgumentException();
        }

        User user = userMapper.toEntity(registerUserDto);
        String hashedPassword = passwordEncoder.encode(registerUserDto.password());
        user.setPassword(hashedPassword);
        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.username())
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Password incorrect");
        }

        return jwtTokenProvider.createToken(user.getEmail());
    }
}