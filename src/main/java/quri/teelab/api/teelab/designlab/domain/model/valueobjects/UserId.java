package quri.teelab.api.teelab.designlab.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record UserId(UUID userId) {
    public UserId {
        if (userId == null || userId.toString().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
    }

    public static UserId of(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or blank");
        }
        var uuid = UUID.fromString(userId);

        return new UserId(uuid);
    }
}
