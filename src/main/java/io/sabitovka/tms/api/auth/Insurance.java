package io.sabitovka.tms.api.auth;

import io.sabitovka.tms.api.exception.ApplicationException;
import io.sabitovka.tms.api.model.entity.Task;
import io.sabitovka.tms.api.model.enums.ErrorCode;
import io.sabitovka.tms.api.model.enums.UserRole;
import io.sabitovka.tms.api.service.AuthService;
import io.sabitovka.tms.api.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Компонент предоставляет функции для выполнения различных проверок, связанных с правами досутпа
 */
@Component
@RequiredArgsConstructor
public class Insurance {
    private final AuthService authService;

    /**
     * Позволяет проверить, есть ли доступ у авторизованного пользователя к задаче.
     * Доступ определяется тем, что пользователь указан как исполнитель
     *
     * @param task Задача, к которой нужно проверить доступ
     */
    public void ensureHasAccessToTheTask(Task task) {
        CustomUserDetails principal = authService.getCurrentUser();
        boolean isAdmin = authService.hasAuthority(UserRole.ADMIN);
        if (!isAdmin && !principal.getUserId().equals(task.getPerformerId())) {
            throw new ApplicationException(ErrorCode.FORBIDDEN, Constants.ACCESS_DENIED_TO_THE_TASK);
        }
    }
}
