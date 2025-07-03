package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

/**
 * Resource representation for creating an Item.
 * Note: This resource does not include an 'id' field as it should be generated automatically.
 */
public record CreateItemResource(
    @NotNull(message = "Product ID cannot be null")
    UUID productId,
    
    @Positive(message = "Quantity must be greater than 0")
    int quantity
) {}
