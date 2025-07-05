package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateFulfillmentCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.OrderId;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.CreateFulfillmentResource;

import java.util.UUID;

public class CreateFulfillmentCommandFromResourceAssembler {

    public static CreateFulfillmentCommand toCommandFromResource(CreateFulfillmentResource resource) {
        // Validate that required fields are not null or empty
        if (resource.orderId() == null || resource.orderId().trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (resource.manufacturerId() == null || resource.manufacturerId().trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer ID cannot be null or empty");
        }

        try {
            return new CreateFulfillmentCommand(
                    new OrderId(UUID.fromString(resource.orderId().trim())),
                    new ManufacturerId(UUID.fromString(resource.manufacturerId().trim()))
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format for orderId or manufacturerId: " + e.getMessage());
        }
    }
}
