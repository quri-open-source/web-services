package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources;

import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;

import java.util.UUID;

public record FulfillmentItemResource(
    String id,
    String productId,
    int quantity,
    FulfillmentItemStatus status,
    UUID fulfillmentId
) {
}
