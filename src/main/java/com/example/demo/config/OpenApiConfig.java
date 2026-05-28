package com.example.demo.config; 

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {

        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()

                // API Information
                .info(new Info()
                        .title("Employee Department Management API")
                        .version("1.0.0")
                        .description(
                                "REST APIs for managing employees and departments "
                              + "using Spring Boot 3 and PostgreSQL."
                        )

                        .contact(new Contact()
                                .name("HoxCloud Development Team")
                                .email("support@hoxcloud.com")
                                .url("https://hoxcloud.com")
                        )

                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                        )
                )

                // Server URLs
                .servers(List.of(

                        new Server()
                                .url("http://localhost:8080/app")
                                .description("Local Development Server"),

                        new Server()
                                .url("https://dev.hoxcloud.com")
                                .description("Development Server"),

                        new Server()
                                .url("https://api.hoxcloud.com")
                                .description("Production Server")
                ))

                // JWT Security
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )

                .components(
                        new Components()

                                .addSecuritySchemes(
                                        securitySchemeName,

                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .description(
                                                        "Enter JWT token in format: Bearer <token>"
                                                )
                                )
                );
    }
}