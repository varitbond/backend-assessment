package com.varit.backend.assessment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "BackEnd Assessment API",
                version = "1.0.0-SNAPSHOT",
                description = "API for access users & posts resource with OIDC Authentication using Auth0.",
                contact = @Contact(
                        name = "Varit Chamroonsawasdi",
                        email = "varit.bond@gmail.com"
                )
        ),
        servers = @Server(
                url = "http://localhost:3000"
        )
)
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                implicit = @OAuthFlow(
                        authorizationUrl = "http://localhost:3000/login",
                        scopes = {@OAuthScope(name = "read/write", description = "Read & Write scope")}
                ))
)
public class OpenApiConfig {
}
