package io.sabitovka.tms.api.service.impl;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.dto.LoginDto;
import io.sabitovka.tms.api.model.dto.RegisterUserDto;
import io.sabitovka.tms.api.model.entity.User;
import io.sabitovka.tms.api.model.enums.UserRole;
import io.sabitovka.tms.api.repository.UserRepository;
import io.sabitovka.tms.api.service.AuthService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тест авторизации в системе")
@SpringBootTest
@ActiveProfiles("test")
@Transactional(isolation = Isolation.READ_UNCOMMITTED)
class AuthServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    private final static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.0")
            .withDatabaseName("testdb")
            .withUsername("junit")
            .withPassword("password");

    @DynamicPropertySource
    private static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgresContainer.getJdbcUrl() + "&currentSchema=model");
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @Test
    @DisplayName("[register] Успешная регистрация")
    void registerShouldCompeteSuccessfully() {
        RegisterUserDto registerUserDto = new RegisterUserDto(
                "user1", "user1@tms.io", "password");

        authService.register(registerUserDto);
        Optional<User> user = userRepository.findByEmail("user1@tms.io");

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getPassword()).isNotEqualTo(registerUserDto.password());
        assertThat(user.get().getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("[register] Если пользователь существует по email, должен выбросить ошибку")
    void registerWhenUserExistsByEmailShouldThrowException() {
        RegisterUserDto registerUserDto = new RegisterUserDto(
                "user1", "user@tms.io", "password");

        assertThatThrownBy(() -> authService.register(registerUserDto))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    @DisplayName("[login] Должен успешно выполнить вход")
    void loginShouldLoginSuccessfully() {
        LoginDto loginDto = new LoginDto("user@tms.io", "password");

        String token = authService.login(loginDto);

        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("[login] Когда неверный логин или пароль, выбросить исключение")
    void loginWhenCredentialsInvalidShouldThrowException() {
        LoginDto userNotExistsLoginDto = new LoginDto("user-not-exists@tms.io", "password");
        LoginDto incorrectPasswordLoginDto = new LoginDto("user-not-exists@tms.io", "password");

        assertThatThrownBy(() -> authService.login(userNotExistsLoginDto))
                .isInstanceOf(ApplicationException.class);

        assertThatThrownBy(() -> authService.login(incorrectPasswordLoginDto))
                .isInstanceOf(ApplicationException.class);
    }
}
