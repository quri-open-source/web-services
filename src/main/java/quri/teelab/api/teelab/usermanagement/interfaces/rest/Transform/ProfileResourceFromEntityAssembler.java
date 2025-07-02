package quri.teelab.api.teelab.usermanagement.interfaces.rest.Transform;

import quri.teelab.api.teelab.usermanagement.domain.Model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources.ProfileResource;

/**
 * Assembler class to convert Profile entity to ProfileResource
 */
public class ProfileResourceFromEntityAssembler {
    
    /**
     * Convert Profile entity to ProfileResource
     * 
     * @param entity Profile entity to convert
     * @return ProfileResource converted from Profile entity
     */
    public static ProfileResource toResourceFromEntity(Profile entity) {
        return new ProfileResource(
            entity.getId(), 
            entity.getFullName(), 
            entity.getEmailAddress(), 
            entity.getStreetAddress()
        );
    }
}