package quri.teelab.api.teelab.orderfulfillment.domain.model.commands;

import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;

public record UpdateFulfillmentItemStatusCommand(FulfillmentItemStatus updateTo, FulfillmentItemId fulfillmentItemId) {
    public UpdateFulfillmentItemStatusCommand {
        if (updateTo == null || fulfillmentItemId == null || fulfillmentItemId.value() == null) {
            throw new IllegalArgumentException("UpdateTo and FulfillmentItem ID cannot be null");
        }
    }
}
