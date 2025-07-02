package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ShoppingCartResource(
    @NotNull UUID shoppingCartId,
    @NotNull UUID userId,
    @NotNull List<ItemResource> items,
    @NotNull BigDecimal totalPrice
) {
    public record ItemResource(
        @NotNull UUID itemId,
        @NotNull UUID projectId,
        int quantity,
        @NotNull BigDecimal unitPrice
    ) {}
}