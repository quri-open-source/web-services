package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.List;
import java.util.UUID;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Address;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;

/**
 * Create new processing order command
 * This command is used to create a new order in the order processing system.
 */

public class CreateOrderCommand {
    private final UUID orderId;
    private final UUID userId;
    private final Address shippingAddress;
    private final List<Item> items;
    private final String description;

    public CreateOrderCommand(UUID orderId,
                              UUID userId,
                              Address shippingAddress,
                              List<Item> items,
                              String description) {
        this.orderId        = orderId;
        this.userId         = userId;
        this.shippingAddress= shippingAddress;
        this.items          = items;
        this.description    = description;
    }

    public UUID getOrderId()         { return orderId; }
    public UUID getUserId()          { return userId; }
    public Address getShippingAddress() { return shippingAddress; }
    public List<Item> getItems()     { return items; }
    public String getDescription()   { return description; }
}
