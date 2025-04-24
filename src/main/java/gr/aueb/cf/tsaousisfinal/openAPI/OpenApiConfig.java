package gr.aueb.cf.tsaousisfinal.openAPI;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info  = @Info (
                contact = @Contact(
                        name = "Nikos Tsaousis",
                        email = "nik.ant.tsaou@gmail.com"
                ),
                description = "OpenAPI  documentation",
                title  = "DormApp API documentation",
                version = "1.0"
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
          @Server(
                  description = "local ENV",
                  url = "http://localhost:8080"
          )
}
)
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)

public class OpenApiConfig {
}
