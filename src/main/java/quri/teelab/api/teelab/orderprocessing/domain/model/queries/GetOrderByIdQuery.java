package quri.teelab.api.teelab.orderprocessing.domain.model.queries;

import java.util.UUID;

public record GetOrderByIdQuery(UUID orderId) {
    public GetOrderByIdQuery {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
    }
}

