package quri.teelab.api.teelab.accesssecurity.infrastructure.authorization;

import org.springframework.stereotype.Service;

import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.ITokenService;
import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Token service implementation using JWT
 */
@Service
public class TokenServiceImpl implements ITokenService {

    private final JwtService jwtService;

    public TokenServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(User user) {
        return jwtService.generateToken(user.getUsername());
    }

    @Override
    public Optional<UUID> validateToken(String token) {
        try {
            jwtService.extractUsername(token);
            // In a real implementation, you would fetch the user from the database
            // For now, we'll return a dummy user ID if token is valid
            return Optional.of(UUID.randomUUID());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
