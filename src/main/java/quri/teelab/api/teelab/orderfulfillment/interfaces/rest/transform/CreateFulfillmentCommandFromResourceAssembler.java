package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateFulfillmentCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.OrderId;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.CreateFulfillmentResource;

import java.util.UUID;

public class CreateFulfillmentCommandFromResourceAssembler {

    public static CreateFulfillmentCommand toCommandFromResource(CreateFulfillmentResource resource) {
        return new CreateFulfillmentCommand(
                new OrderId(UUID.fromString(resource.orderId())),
                new ManufacturerId(UUID.fromString(resource.manufacturerId()))
        );
    }
}
