package quri.teelab.api.teelab.orderprocessing.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.orderprocessing.domain.services.ShoppingCartCommandService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.ShoppingCartRepository;

@Service
public class ShoppingCartCommandServiceImpl implements ShoppingCartCommandService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartCommandServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart handle(CreateShoppingCartCommand command) {
        if (shoppingCartRepository.existsByUserId(command.userId()))
            throw new IllegalArgumentException("Shopping cart for user already exists.");
        var cart = new ShoppingCart(command);
        shoppingCartRepository.save(cart);
        return cart;
    }

    @Override
    public void handle(AddItemToShoppingCartCommand command) {
        var cart = shoppingCartRepository.findById(command.shoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found."));
        var item = new Item(
                command.itemId(),
                command.projectId(),
                command.quantity(),
                command.unitPrice()
        );
        cart.addItem(item);
        shoppingCartRepository.save(cart);
    }

    @Override
    public void handle(RemoveItemFromShoppingCartCommand command) {
        var cart = shoppingCartRepository.findById(command.shoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found."));
        cart.removeItem(command.itemId());
        shoppingCartRepository.save(cart);
    }

    @Override
    public void handle(UpdateItemQuantityCommand command) {
        var cart = shoppingCartRepository.findById(command.shoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found."));
        var originalItem = cart.getItems().stream()
                .filter(i -> i.getId().equals(command.itemId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart."));
        cart.removeItem(command.itemId());
        var updatedItem = new Item(
                command.itemId(),
                originalItem.getProjectId(),
                command.newQuantity(),
                originalItem.getUnitPrice()
        );
        cart.addItem(updatedItem);
        shoppingCartRepository.save(cart);
    }

    @Override
    public void handle(ClearShoppingCartCommand command) {
        var cart = shoppingCartRepository.findById(command.shoppingCartId())
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found."));
        cart.getItems().forEach(item -> cart.removeItem(item.getId()));
        shoppingCartRepository.save(cart);
    }
}