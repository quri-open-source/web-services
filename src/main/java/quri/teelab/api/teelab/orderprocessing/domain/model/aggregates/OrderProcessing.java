package quri.teelab.api.teelab.orderprocessing.domain.model.aggregates;

import jakarta.persistence.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Root aggregate for order processing.
 * This class represents the order processing aggregate in the domain model.
 * It encapsulates the order details, user information, status, shipping address,
 */

@Entity
@Table(name = "order_processing")
public class OrderProcessing extends AuditableAbstractAggregateRoot<OrderProcessing> {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Item> items;


    protected OrderProcessing() {
        // Constructor para JPA
        this.items = new ArrayList<>();
    }

    public OrderProcessing(CreateOrderCommand command) {
        this.userId = command.getUserId();
        this.items = (command.getItems() == null) ? new ArrayList<>() : new ArrayList<>(command.getItems());

    }

    /**
     * Expone el valor del identificador del agregado.
     */

    // Getters
    public UUID getUserId() { return userId; }
    public List<Item> getItems() { return List.copyOf(items); }
    public UUID getId() { return super.getId(); }


    @Override
    protected OrderProcessing self() {
        return this;
    }
}
