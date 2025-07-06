package quri.teelab.api.teelab.orderfulfillment.domain.model.queries;

import java.util.UUID;

public record GetAllFulfillmentItemsByFulfillmentIdQuery(UUID fulfillmentId) {
    public GetAllFulfillmentItemsByFulfillmentIdQuery {
        if (fulfillmentId == null) {
            throw new IllegalArgumentException("Fulfillment ID cannot be null");
        }
    }
}
