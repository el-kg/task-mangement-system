package com.mobile.effective.task_management_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration class.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures the OpenAPI bean for Swagger documentation.
     *
     * @return the OpenAPI instance configured with API information
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .version("1.0")
                        .description("API для управления задачами"));
    }
}
