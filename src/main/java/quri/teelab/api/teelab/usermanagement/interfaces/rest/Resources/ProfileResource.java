package quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources;

/**
 * Profile resource for REST API
 */
public record ProfileResource(
    Long id,
    String fullName,
    String email,
    String streetAddress
) {
}