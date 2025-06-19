package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record CreateOrderResource(
        @NotNull UUID userId,
        @NotNull AddressResource shippingAddress,
        @NotNull List<ItemResource> items,
        String description
) {}