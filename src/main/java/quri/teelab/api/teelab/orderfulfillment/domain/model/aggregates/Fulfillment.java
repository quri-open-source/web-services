package quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateFulfillmentCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.*;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "fulfillments", indexes = {@Index(name = "idx_fulfillments_order_id", columnList = "order_id"), @Index(name = "idx_fulfillments_manufacturer_id", columnList = "manufacturer_id")})
public class Fulfillment extends AuditableAbstractAggregateRoot<Fulfillment> {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "order_id", nullable = false))
    private OrderId orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FulfillmentStatus status;

    @Column(name = "received_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;

    @Column(name = "shipped_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date shippedDate;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "fulfillment_id", nullable = false)
    private List<FulfillmentItem> items = new ArrayList<>();

    public Fulfillment() {
        // Default constructor for JPA
    }

    public Fulfillment(OrderId orderId, FulfillmentStatus status, Date receivedDate, Date shippedDate, Manufacturer manufacturer) {
        this.orderId = orderId;
        this.status = status;
        this.receivedDate = receivedDate;
        this.shippedDate = shippedDate;
        this.manufacturer = manufacturer;

        // Maintain bidirectional relationship
        if (manufacturer != null) {
            manufacturer.addFulfillment(this);
        }
    }    // Constructor for creating new fulfillments (status is "PENDING", receivedDate and shippedDate are null by default)    // This constructor is used by the application service which should look up the manufacturer

    public Fulfillment(CreateFulfillmentCommand command, Manufacturer manufacturer) {
        this.orderId = command.orderId();
        this.status = FulfillmentStatus.PENDING; // Automatically set to "PENDING" for new fulfillments
        this.receivedDate = null; // Automatically set to null for new fulfillments (not yet received by manufacturer)
        this.shippedDate = null; // Automatically set to null for new fulfillments
        this.manufacturer = manufacturer;

        // Maintain bidirectional relationship
        if (manufacturer != null) {
            manufacturer.addFulfillment(this);
        }
    }

    public Fulfillment(CreateFulfillmentCommand command) {
        this.orderId = command.orderId();
        this.status = FulfillmentStatus.PENDING;
        this.receivedDate = null;
        this.shippedDate = null;
        // The manufacturer reference is not set here, which can cause issues
        // This constructor should not be used in new code
    }

    public void updateStatus(FulfillmentStatus status) {
        this.status = status;
    }

    public void updateReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void updateShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public void addItem(FulfillmentItem item) {
        items.add(item);
    }

    public void removeItem(FulfillmentItem item) {
        items.remove(item);
    }

    public List<FulfillmentItem> getItems() {
        return new ArrayList<>(items);
    }

    public FulfillmentItem createItem(ProductId productId, int quantity, FulfillmentItemStatus status) {
        FulfillmentItem item = new FulfillmentItem(FulfillmentItemId.newId(), productId, quantity, status, this.getId());
        addItem(item);
        return item;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public UUID getManufacturerId() {
        return manufacturer != null ? manufacturer.getId() : null;
    }
}
