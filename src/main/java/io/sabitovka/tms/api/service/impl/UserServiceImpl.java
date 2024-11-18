package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.entity.User;
import io.sabitovka.tms.api.model.dto.UserInfoDto;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.service.UserService;
import io.sabitovka.tms.api.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void update(Long userId, UserInfoDto userInfoDto, UserDetails userDetails) {

    }
}
