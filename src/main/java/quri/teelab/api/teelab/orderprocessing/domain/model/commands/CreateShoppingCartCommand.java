package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;

// Comando para crear un nuevo carrito de compras (ShoppingCart) sin projectId
public record CreateShoppingCartCommand(UUID id, UUID userId) {
    public CreateShoppingCartCommand {
        if (id == null) throw new IllegalArgumentException("id no puede ser null");
        if (userId == null) throw new IllegalArgumentException("userId no puede ser null");
    }
}
