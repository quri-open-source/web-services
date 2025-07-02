package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderprocessing.application.internal.commandservices.ShoppingCartCommandServiceImpl;
import quri.teelab.api.teelab.orderprocessing.application.internal.queryservices.ShoppingCartQueryServiceImpl;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ClearShoppingCartCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateShoppingCartCommand;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ShoppingCartResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.ShoppingCartAssembler;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/shopping-carts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Shopping Carts")
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

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId) {
        try {
            commandService.handle(new ClearShoppingCartCommand(cartId));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}