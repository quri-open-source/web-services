package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.UpdateFulfillmentItemStatusCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.UpdateFulfillmentItemStatusResource;

import java.util.UUID;

public class CreateUpdateFulfillmentItemStatusCommandFromResource {
    public static UpdateFulfillmentItemStatusCommand toCommandFromResource(UpdateFulfillmentItemStatusResource resource, String fulfillmentItemId) {
        return new UpdateFulfillmentItemStatusCommand(
                FulfillmentItemStatus.valueOf(resource.updateTo()),
                new FulfillmentItemId(UUID.fromString(fulfillmentItemId))
        );
    }
}
