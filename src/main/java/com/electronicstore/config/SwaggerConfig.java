package com.electronicstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth"; // changed from JWT

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, jwtSecurityScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("Electronic Store Backend API")
                .description("This is an Electronic Store project using Java Spring Boot and React.")
                .version("1.0.0")
                .termsOfService("https://www.code_learners.com")
                .contact(new Contact()
                        .name("Nagaraju")
                        .url("https://www.instagram.com/abcd")
                        .email("nagaraju1819@gmail.com"))
                .license(new io.swagger.v3.oas.models.info.License()
                        .name("LICENSES OF API")
                        .url("https://www.learncodes.com/about"));
    }

    private SecurityScheme jwtSecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .description("JWT token header using the Bearer scheme. Example: 'Bearer {token}'")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
