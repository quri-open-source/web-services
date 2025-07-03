package quri.teelab.api.teelab.orderfulfillment.domain.model.commands;

import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;

public record MarkFulfillmentItemAsShippedCommand(FulfillmentItemId fulfillmentItemId) {
    public MarkFulfillmentItemAsShippedCommand {
        if (fulfillmentItemId == null || fulfillmentItemId.value() == null) {
            throw new IllegalArgumentException("FulfillmentItem ID cannot be null");
        }
    }
}
