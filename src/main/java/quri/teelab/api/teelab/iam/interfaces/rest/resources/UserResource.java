package quri.teelab.api.teelab.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(String id, String username, List<String> roles) {
}
