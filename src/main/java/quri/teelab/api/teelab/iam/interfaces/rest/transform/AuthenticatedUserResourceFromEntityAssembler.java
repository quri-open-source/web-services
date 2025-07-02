package quri.teelab.api.teelab.iam.interfaces.rest.transform;

import quri.teelab.api.teelab.iam.domain.model.aggregates.User;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId().toString(), user.getUsername(), token);
    }
}
