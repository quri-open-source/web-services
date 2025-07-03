package quri.teelab.api.teelab.productcatalog.domain.model.queries;

import java.util.UUID;

public record CheckIfUserLikedProductQuery(
        UUID productId,
        UUID userId
) {
    public CheckIfUserLikedProductQuery {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
