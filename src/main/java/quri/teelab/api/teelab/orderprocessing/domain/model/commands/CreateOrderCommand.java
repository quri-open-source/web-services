package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;

import java.util.List;
import java.util.UUID;

/**
 * Create new processing order command
 * This command is used to create a new order in the order processing system.
 */

public class CreateOrderCommand {

    private final UUID userId;
    private final List<Item> items;

    public CreateOrderCommand(
                              UUID userId,
                              List<Item> items) {

        this.userId         = userId;
        this.items          = items;
    }


    public UUID getUserId()          { return userId; }
    public List<Item> getItems()     { return items; }

}
