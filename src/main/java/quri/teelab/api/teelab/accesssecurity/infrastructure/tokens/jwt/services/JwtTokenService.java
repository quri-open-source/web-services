package quri.teelab.api.teelab.accesssecurity.infrastructure.tokens.jwt.services;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.ITokenService;
import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.infrastructure.authorization.JwtService;

import java.util.Optional;
import java.util.UUID;

/**
 * JWT token service implementation
 */
@Service
@Primary
public class JwtTokenService implements ITokenService {

    private final JwtService jwtService;

    public JwtTokenService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(User user) {
        return jwtService.generateTokenWithUserId(user.getUsername(), user.getId());
    }

    @Override
    public Optional<UUID> validateToken(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return Optional.empty();
            }
            
            String username = jwtService.extractUsername(token);
            if (username != null && jwtService.isTokenValid(token, username)) {
                // Extract user ID from token claims if available
                Claims claims = jwtService.extractClaim(token, claims1 -> claims1);
                if (claims.containsKey("userId")) {
                    Object userIdClaim = claims.get("userId");
                    UUID userId = UUID.fromString(userIdClaim.toString());
                    return Optional.of(userId);
                }
                // For backward compatibility, return empty if no userId claim
                return Optional.empty();
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
