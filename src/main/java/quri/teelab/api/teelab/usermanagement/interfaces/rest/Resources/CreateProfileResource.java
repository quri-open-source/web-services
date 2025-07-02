package quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources;

/**
 * Resource for creating a new profile
 */
public record CreateProfileResource(
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