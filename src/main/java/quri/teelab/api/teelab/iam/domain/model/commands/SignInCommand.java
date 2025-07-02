package quri.teelab.api.teelab.iam.domain.model.commands;

/**
 * Sign in command
 * <p>
 *     This class represents the command to sign in a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see quri.teelab.api.teelab.iam.domain.model.aggregates.User
 */
public record SignInCommand(String username, String password) {
}
