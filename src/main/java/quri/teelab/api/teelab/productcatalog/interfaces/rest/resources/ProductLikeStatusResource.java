package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.util.UUID;

public record ProductLikeStatusResource(
        String message,
        UUID productId,
        UUID userId
) {
    public ProductLikeStatusResource {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
