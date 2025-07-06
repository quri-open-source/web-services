package quri.teelab.api.teelab.iam.interfaces.rest.transform;

import quri.teelab.api.teelab.iam.domain.model.entities.Role;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
