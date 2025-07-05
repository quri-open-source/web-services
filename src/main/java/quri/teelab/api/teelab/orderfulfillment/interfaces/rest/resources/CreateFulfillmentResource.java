package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateFulfillmentResource(
        @NotBlank(message = "Order ID is required")
        String orderId,
        @NotBlank(message = "Manufacturer ID is required") 
        String manufacturerId
) {
}
