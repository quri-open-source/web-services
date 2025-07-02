package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;

// Clase record que representa el comando para vaciar el carrito de compras.
public record ClearShoppingCartCommand(UUID shoppingCartId) {
    public ClearShoppingCartCommand {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
    }
}