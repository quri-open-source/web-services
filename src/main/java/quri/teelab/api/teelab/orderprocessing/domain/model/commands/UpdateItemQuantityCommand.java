package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;

// Clase record que representa el comando para actualizar la cantidad de un ítem en el carrito de compras.
public record UpdateItemQuantityCommand(UUID shoppingCartId, UUID itemId, int newQuantity) {
    public UpdateItemQuantityCommand {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
        if (itemId == null) throw new IllegalArgumentException("itemId cannot be null");
        if (newQuantity <= 0) throw new IllegalArgumentException("newQuantity must be greater than zero");
    }
}