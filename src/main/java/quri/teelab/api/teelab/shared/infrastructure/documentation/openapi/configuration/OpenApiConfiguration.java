package quri.teelab.api.teelab.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration class
 * This class configures the OpenAPI documentation including security schemes
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI teeLabOpenApi() {
        // Define JWT Bearer token security scheme
        var jwtSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Authorization header using the Bearer scheme. Example: \"Authorization: Bearer {token}\"");

        return new OpenAPI()
                .info(new Info()
                        .title("TeeLab API")
                        .description("Sistema de gestión para TeeLab - API REST para el manejo de usuarios, diseños, catálogo de productos y análisis")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TeeLab Development Team")
                                .email("support@teelab.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Development server"))
                .addServersItem(new Server()
                        .url("https://api.teelab.com")
                        .description("Production server"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", jwtSecurityScheme));
    }
}
