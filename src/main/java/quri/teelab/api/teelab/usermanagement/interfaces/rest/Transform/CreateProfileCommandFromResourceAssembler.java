package quri.teelab.api.teelab.usermanagement.interfaces.rest.Transform;

import quri.teelab.api.teelab.usermanagement.domain.model.commands.CreateProfileCommand;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources.CreateProfileResource;

/**
 * Assembler to create a CreateProfileCommand command from a resource
 */
public class CreateProfileCommandFromResourceAssembler {
    
    /**
     * Create a CreateProfileCommand command from a resource
     * 
     * @param resource The CreateProfileResource resource to create the command from
     * @return The CreateProfileCommand command created from the resource
     */
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
            resource.firstName(),
            resource.lastName(),
            resource.email(),
            resource.street(),
            resource.number(),
            resource.city(),
            resource.postalCode(),
            resource.country()
        );
    }
}