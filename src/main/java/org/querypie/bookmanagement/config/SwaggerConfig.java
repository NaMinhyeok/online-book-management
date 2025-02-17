package org.querypie.bookmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Book Management API",
        version = "v1"
    )
)
public class SwaggerConfig {
}
