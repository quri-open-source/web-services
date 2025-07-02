package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderprocessing.application.internal.commandservices.ShoppingCartCommandServiceImpl;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.*;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ShoppingCartResource;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/shopping-cart/{cartId}/items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Shopping Cart Items")
public class ShoppingCartItemController {

    private final ShoppingCartCommandServiceImpl commandService;

    public ShoppingCartItemController(ShoppingCartCommandServiceImpl commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@PathVariable UUID cartId,
                                        @RequestBody @Valid ShoppingCartResource.ItemResource item) {
        try {
            commandService.handle(new AddItemToShoppingCartCommand(
                    cartId, item.itemId(), item.projectId(), item.quantity(), item.unitPrice()
            ));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItemQuantity(@PathVariable UUID cartId,
                                                   @PathVariable UUID itemId,
                                                   @RequestParam("quantity") int quantity) {
        try {
            commandService.handle(new UpdateItemQuantityCommand(cartId, itemId, quantity));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID cartId,
                                           @PathVariable UUID itemId) {
        try {
            commandService.handle(new RemoveItemFromShoppingCartCommand(cartId, itemId));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
