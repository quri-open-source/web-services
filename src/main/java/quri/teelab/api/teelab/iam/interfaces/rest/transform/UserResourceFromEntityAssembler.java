package quri.teelab.api.teelab.iam.interfaces.rest.transform;

import quri.teelab.api.teelab.iam.domain.model.aggregates.User;
import quri.teelab.api.teelab.iam.domain.model.entities.Role;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId().toString(), user.getUsername(), roles);
    }
}
