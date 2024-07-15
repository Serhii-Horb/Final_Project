package com.example.final_project.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Online Shop",
                description = "Online Shop", version = "1.0.0",
                contact = @Contact(
                        name = "Horb Serhii, Daniil Kienko, Artemii Olefirenko",
                        email = "Sergey.gorb777@gmail.com",
                        url = "https://api.example.com/v1"
                )
        ),
        security = @SecurityRequirement(name = "JWT")

)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
