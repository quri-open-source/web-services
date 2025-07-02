package quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.AuthenticatedUserResource;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.UserResource;

/**
 * User resource from entity assembler
 */
public class UserResourceFromEntityAssembler {
    
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(entity.getId(), entity.getUsername());
    }
    
    public static AuthenticatedUserResource toAuthenticatedResourceFromEntity(User entity, String token) {
        return new AuthenticatedUserResource(entity.getId(), entity.getUsername(), token);
    }
}
