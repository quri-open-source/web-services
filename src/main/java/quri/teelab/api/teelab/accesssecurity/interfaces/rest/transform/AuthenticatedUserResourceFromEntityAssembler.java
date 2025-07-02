package quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform;

import org.springframework.stereotype.Component;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Authenticated User Resource From Entity Assembler
 * This class provides static methods to transform User entities to AuthenticatedUserResource DTOs
 */
@Component
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Transforms a User entity and token to an AuthenticatedUserResource
     * 
     * @param user The user entity to transform
     * @param token The authentication token
     * @return AuthenticatedUserResource containing user data and token
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }

    /**
     * Transforms a User entity and token to an AuthenticatedUserResource with null validation
     * 
     * @param user The user entity to transform (can be null)
     * @param token The authentication token (can be null)
     * @return AuthenticatedUserResource or null if user is null
     */
    public static AuthenticatedUserResource toResourceFromEntitySafe(User user, String token) {
        if (user == null) {
            return null;
        }
        return new AuthenticatedUserResource(
            user.getId(), 
            user.getUsername(), 
            token != null ? token : ""
        );
    }
}