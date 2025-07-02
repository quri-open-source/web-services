package quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform;

import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignInCommand;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.SignInResource;

/**
 * Sign in command from resource assembler
 */
public class SignInCommandFromResourceAssembler {
    
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.username(), resource.password());
    }
}
