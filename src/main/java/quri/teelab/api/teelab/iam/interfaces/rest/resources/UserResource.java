package quri.teelab.api.teelab.iam.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record UserResource(UUID id, String username, List<String> roles) {
}
