package quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices;

import java.util.Optional;
import java.util.UUID;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;

/**
 * The token service interface
 * This interface is used to generate and validate JWT tokens
 */
public interface ITokenService {
    
    /**
     * Generate a JWT token
     * @param user The user to generate the token for
     * @return The generated token
     */
    String generateToken(User user);

    /**
     * Validate a JWT token
     * @param token The token to validate
     * @return The user id if the token is valid, empty otherwise
     */
    Optional<UUID> validateToken(String token);
}