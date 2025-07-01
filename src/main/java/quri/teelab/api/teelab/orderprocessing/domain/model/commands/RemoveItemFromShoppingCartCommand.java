package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;

// Clase record que representa el comando para eliminar(remove) un ítem del carrito de compras.
public record RemoveItemFromShoppingCartCommand(UUID shoppingCartId, UUID itemId) {
    public RemoveItemFromShoppingCartCommand {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
        if (itemId == null) throw new IllegalArgumentException("itemId cannot be null");
    }
}