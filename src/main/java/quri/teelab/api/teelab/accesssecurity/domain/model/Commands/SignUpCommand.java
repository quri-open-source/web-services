package quri.teelab.api.teelab.accesssecurity.domain.model.Commands;

/**
 * The sign up command
 * This command object includes the username and password to sign up
 */
public record SignUpCommand(String username, String password) {
}