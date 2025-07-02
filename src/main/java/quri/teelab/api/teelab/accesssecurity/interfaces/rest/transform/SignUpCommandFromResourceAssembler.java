package quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform;

import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignUpCommand;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.SignUpResource;

/**
 * Sign up command from resource assembler
 */
public class SignUpCommandFromResourceAssembler {
    
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(resource.username(), resource.password());
    }
}
