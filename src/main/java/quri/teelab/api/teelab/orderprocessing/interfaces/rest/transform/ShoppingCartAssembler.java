package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ShoppingCartResource;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartAssembler {
    public static ShoppingCartResource toResource(ShoppingCart cart) {
        List<ShoppingCartResource.ItemResource> items = cart.getItems().stream()
                .map(ShoppingCartAssembler::toItemResource)
                .collect(Collectors.toList());
        return new ShoppingCartResource(
                cart.getId(),
                cart.getUserId(),
                items,
                cart.getTotalPrice()
        );
    }

    private static ShoppingCartResource.ItemResource toItemResource(Item item) {
        return new ShoppingCartResource.ItemResource(
                item.getId(),
                item.getProjectId(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }
}
