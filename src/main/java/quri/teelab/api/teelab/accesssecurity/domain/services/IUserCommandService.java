package quri.teelab.api.teelab.accesssecurity.domain.services;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignInCommand;
import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignUpCommand;

/**
 * The user command service
 * This interface is used to handle user commands
 */
public interface IUserCommandService {
    
    /**
     * Handle sign in command
     * @param command The sign in command
     * @return The authenticated user and the JWT token
     */
    AuthenticatedUser handle(SignInCommand command);

    /**
     * Handle sign up command
     * @param command The sign up command
     * @return The created user
     */
    User handle(SignUpCommand command);
    
    /**
     * Inner class for authenticated user response
     */
    record AuthenticatedUser(User user, String token) {}
}