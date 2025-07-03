package quri.teelab.api.teelab.productcatalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing a User ID within the Product Catalog domain.
 * This is used for tracking likes and user interactions with products.
 */
@Embeddable
public record UserId(UUID value) implements Serializable {

    /**
     * Creates a new UserId with validation.
     */
    public UserId {
        Objects.requireNonNull(value, "User ID cannot be null");
    }

    /**
     * Factory method to create a UserId from a UUID.
     */
    public static UserId of(UUID id) {
        return new UserId(id);
    }

    /**
     * Factory method to create a UserId from a string.
     */
    public static UserId of(String id) {
        return new UserId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
