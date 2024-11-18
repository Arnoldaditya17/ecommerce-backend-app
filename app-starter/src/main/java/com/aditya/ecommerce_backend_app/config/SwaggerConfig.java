package com.aditya.ecommerce_backend_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "aditya",
                        email = "adityasinghrajput02@gmail.com",
                        url = "https://adityasinghrajput.tech"),
                description = "swagger configuration for springboot e-commerce application",
                title = "OpenApi Specification - aditya singh rajput ",
                version = "1.0",
                license = @License(
                        name = "@all rights reserved 2024",
                        url = "https://adityasinghrajput.tech"

                ),
                termsOfService = "Terms of Service"

        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://192.168.29.10:8080"
                ),

                @Server(
                        description = " PROD ENV",
                        url = "https://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }

)
@SecuritySchemes(
        {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "JWT auth description",
                        scheme = "bearer",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER
                ),

        }
)


public class SwaggerConfig {

}
