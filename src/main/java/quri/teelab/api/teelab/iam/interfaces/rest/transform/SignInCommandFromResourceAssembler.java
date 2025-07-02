package quri.teelab.api.teelab.iam.interfaces.rest.transform;

import quri.teelab.api.teelab.iam.domain.model.commands.SignInCommand;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
