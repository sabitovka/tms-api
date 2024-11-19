package io.sabitovka.tms.api.config;

import io.sabitovka.tms.api.util.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info =
@Info(
        title = "Task Management System API",
        version = "1.0.0",
        description = "Task Management System - Простой трекер задач",
        contact = @Contact(name = "Karim Sabitov")
)
)
@SecurityScheme(
        name = Constants.BEARER_AUTHORIZATION,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig {
}
