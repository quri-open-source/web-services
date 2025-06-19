package quri.teelab.api.teelab.orderprocessing.domain.model.aggregates;

import jakarta.persistence.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Discount;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Address;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId;

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

    @EmbeddedId
    private OrderId id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String status;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "address_street")),
            @AttributeOverride(name = "city",    column = @Column(name = "address_city")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country")),
            @AttributeOverride(name = "state",   column = @Column(name = "address_state")),
            @AttributeOverride(name = "zip",     column = @Column(name = "address_zip"))
    })
    private Address shippingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "total_amount",  nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_currency", length = 3, nullable = false))
    })
    private Money totalAmount;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column
    private String description;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<Item> items = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "order_discounts", joinColumns = @JoinColumn(name = "order_id"))
    private List<Discount> appliedDiscounts = new ArrayList<>();

    protected OrderProcessing() {
        // Constructor para JPA
    }

    public OrderProcessing(CreateOrderCommand command) {
        this.id = OrderId.of(command.getOrderId());
        this.userId = command.getUserId();
        this.status = "pending";
        this.shippingAddress = command.getShippingAddress();
        this.items = new ArrayList<>(command.getItems());
        this.appliedDiscounts = new ArrayList<>();
        this.totalAmount = this.items.stream()
                .map(i -> Money.of(i.getUnitPrice()).multiply(i.getQuantity()))
                .reduce(new Money(), Money::add);
        this.description = command.getDescription();
        // createdAt y updatedAt los maneja la clase base AuditableAbstractAggregateRoot
    }

    public void process(String paymentMethod) {
        if (!"pending".equals(this.status)) {
            throw new IllegalStateException("Only pending orders can be processed.");
        }
        this.status = "processed";
        this.transactionId = UUID.randomUUID().toString();
    }

    public void cancel() {
        if ("processed".equals(this.status)) {
            throw new IllegalStateException("Cannot cancel a processed order.");
        }
        this.status = "canceled";
    }

    /**
     * Expone el valor del identificador del agregado.
     */
    public OrderId orderId() {
        return id;
    }

    // Getters
    public UUID getUserId() { return userId; }
    public String getStatus() { return status; }
    public Address getShippingAddress() { return shippingAddress; }
    public Money getTotalAmount() { return totalAmount; }
    public String getTransactionId() { return transactionId; }
    public String getDescription() { return description; }
    public List<Item> getItems() { return List.copyOf(items); }
    public List<Discount> getAppliedDiscounts() { return List.copyOf(appliedDiscounts); }

    @Override
    protected OrderProcessing self() {
        return this;
    }
}
