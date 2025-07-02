package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.*;

public interface ShoppingCartCommandService {
    // Maneja el comando para crear un nuevo carrito de compras
    ShoppingCart handle(CreateShoppingCartCommand command);
    void handle(AddItemToShoppingCartCommand command);
    void handle(RemoveItemFromShoppingCartCommand command);
    void handle(UpdateItemQuantityCommand command);
    void handle(ClearShoppingCartCommand command);

}