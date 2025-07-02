package quri.teelab.api.teelab.usermanagement.domain.Model.commands;

/**
 * Create Profile Command 
 * 
 * @param firstName The first name of the profile.
 * @param lastName The last name of the profile.
 * @param email The email address of the profile.
 * @param street The street address of the profile.
 * @param number The number of the street address for the profile.
 * @param city The city of the profile.
 * @param postalCode The postal code of the profile.
 * @param country The country of the profile.
 */
public record CreateProfileCommand(
    String firstName, 
    String lastName, 
    String email, 
    String street, 
    String number, 
    String city, 
    String postalCode, 
    String country
) {
}