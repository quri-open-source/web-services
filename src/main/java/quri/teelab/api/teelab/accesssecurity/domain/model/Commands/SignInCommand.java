package quri.teelab.api.teelab.accesssecurity.domain.model.Commands;

/**
 * The sign in command
 * This command object includes the username and password to sign in
 */
public record SignInCommand(String username, String password) {
}