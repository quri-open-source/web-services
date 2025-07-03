package quri.teelab.api.teelab.productcatalog.domain.model.commands;

import java.util.UUID;

public record LikeProductCommand(
        UUID productId,
        UUID userId
) {
    public LikeProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
