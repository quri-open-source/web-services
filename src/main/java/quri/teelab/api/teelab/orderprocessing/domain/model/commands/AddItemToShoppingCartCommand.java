package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;
import java.math.BigDecimal;

// Clase record que representa el comando para agregar un ítem al carrito de compras.
public record AddItemToShoppingCartCommand(
    UUID shoppingCartId,
    UUID itemId,
    UUID projectId,
    int quantity,
    BigDecimal unitPrice
) {
    public AddItemToShoppingCartCommand {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
        if (itemId == null) throw new IllegalArgumentException("itemId cannot be null");
        if (projectId == null) throw new IllegalArgumentException("projectId cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be greater than zero");
        if (unitPrice == null || unitPrice.signum() < 0) throw new IllegalArgumentException("unitPrice must be non-negative");
    }
}