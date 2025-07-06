package quri.teelab.api.teelab.orderprocessing.domain.model.queries;

import java.util.UUID;

public record GetOrdersByUserIdQuery(UUID userId) {
    public GetOrdersByUserIdQuery {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}

