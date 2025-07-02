package quri.teelab.api.teelab.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration class
 * This class configures the OpenAPI documentation including security schemes
 */
@Configuration
public class OpenApiConfiguration {

    @Value("${spring.application.name")
    String applicationName;

    @Value("${documentation.application.description}")
    String applicationDescription;

    @Value("${documentation.application.version}")
    String applicationVersion;


    @Bean
    public OpenAPI teeLabOpenApi() {
        // Define JWT Bearer token security scheme
        var openApi = new OpenAPI();


        openApi.info(new Info()
                .title(this.applicationName)
                .description(this.applicationDescription)
                .version(this.applicationVersion)
                .license(new License().name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0"))).externalDocs(
                        new ExternalDocumentation().description("TeeLab wiki Documentation")
                        .url("https://teelab.wiki.github.io/docs"));

        final String securitySchemeName = "bearerAuth";

        openApi.addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));


        return openApi;
    }
}
