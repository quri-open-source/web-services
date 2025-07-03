package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Resource representation for creating an Order.
 * Used at the API boundary layer as part of the anti-corruption layer pattern.
 */
public record CreateOrderResource(
        @NotNull(message = "User ID cannot be null")
        UUID userId,
        
        @NotNull(message = "Items list cannot be null")
        @NotEmpty(message = "Items list cannot be empty")
        @Valid
        List<CreateItemResource> items
) {}