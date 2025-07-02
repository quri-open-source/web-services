package quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources;

import java.util.UUID;

/**
 * Authenticated user resource
 */
public record AuthenticatedUserResource(UUID id, String username, String token) {
}
