package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.CreateOrderResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateOrderCommandFromResourceAssembler {

    public static CreateOrderCommand toCommand(CreateOrderResource resource) {
        List<Item> items = (resource.items() == null) ? List.of() : resource.items().stream()
                .map(itemRes -> new Item(
                        null, // Let Hibernate generate the ID
                        itemRes.productId(),
                        itemRes.quantity()
                ))
                .collect(Collectors.toList());

        return new CreateOrderCommand(
                resource.userId(),
                items
        );
    }
}