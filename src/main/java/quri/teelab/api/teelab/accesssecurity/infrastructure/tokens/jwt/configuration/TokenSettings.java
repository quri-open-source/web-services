package quri.teelab.api.teelab.accesssecurity.infrastructure.tokens.jwt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is used to store the token settings.
 * It is used to configure the token settings in the application properties file.
 */
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class TokenSettings {
    
    private String secret;
    private Long expiration = 86400000L; // 24 hours default

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }
}