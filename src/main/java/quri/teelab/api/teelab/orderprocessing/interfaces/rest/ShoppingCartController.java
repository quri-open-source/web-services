package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderprocessing.application.internal.commandservices.ShoppingCartCommandServiceImpl;
import quri.teelab.api.teelab.orderprocessing.application.internal.queryservices.ShoppingCartQueryServiceImpl;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetAllItemsInShoppingCartQuery;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ShoppingCartResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.ShoppingCartAssembler;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/shopping-cart", produces = APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    private final ShoppingCartCommandServiceImpl commandService;
    private final ShoppingCartQueryServiceImpl queryService;

    public ShoppingCartController(ShoppingCartCommandServiceImpl commandService,
                                  ShoppingCartQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<ShoppingCartResource> getCartByUser(@RequestParam("userId") UUID userId) {
        var cartOpt = queryService.findByUserId(userId);
        if (cartOpt.isEmpty()) return ResponseEntity.notFound().build();
        var cart = cartOpt.get();
        ShoppingCartResource resource = ShoppingCartAssembler.toResource(cart);
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<Void> createCart(@RequestParam("userId") UUID userId) {
        try {
            var cartId = UUID.randomUUID();
            commandService.handle(new CreateShoppingCartCommand(cartId, userId));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(@RequestParam("shoppingCartId") UUID cartId,
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

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Void> updateItemQuantity(@RequestParam("shoppingCartId") UUID cartId,
                                                   @PathVariable UUID itemId,
                                                   @RequestParam("quantity") int quantity) {
        try {
            commandService.handle(new UpdateItemQuantityCommand(cartId, itemId, quantity));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@RequestParam("shoppingCartId") UUID cartId,
                                           @PathVariable UUID itemId) {
        try {
            commandService.handle(new RemoveItemFromShoppingCartCommand(cartId, itemId));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam("shoppingCartId") UUID cartId) {
        try {
            commandService.handle(new ClearShoppingCartCommand(cartId));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}