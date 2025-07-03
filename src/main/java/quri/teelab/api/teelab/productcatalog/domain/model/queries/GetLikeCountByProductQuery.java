package quri.teelab.api.teelab.productcatalog.domain.model.queries;

import java.util.UUID;

public record GetLikeCountByProductQuery(
        UUID productId
) {
    public GetLikeCountByProductQuery {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}
