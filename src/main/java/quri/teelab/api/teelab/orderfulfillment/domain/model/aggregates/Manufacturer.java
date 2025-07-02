package quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentStatus;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.OrderId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends AuditableAbstractAggregateRoot<Manufacturer> {
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false, unique = true))
    private UserId userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fulfillment> fulfillments = new ArrayList<>();

    public Manufacturer() {
        // Default constructor for JPA
    }

    public Manufacturer(String userId, String name, String address, String city, String country, String state, String zip) {
        this.userId = new UserId(UUID.fromString(userId));
        this.name = name;
        this.address = new Address(address, city, country, state, zip);
    }

    public void updateInformation(String name, String address, String city, String country, String state, String zip) {
        this.name = name;
        this.address = new Address(address, city, country, state, zip);
    }

    public void addFulfillment(Fulfillment fulfillment) {
        fulfillments.add(fulfillment);
    }

    public void removeFulfillment(Fulfillment fulfillment) {
        fulfillments.remove(fulfillment);
    }

    public List<Fulfillment> getFulfillments() {
        return new ArrayList<>(fulfillments);
    }

    public Fulfillment createFulfillment(OrderId orderId, FulfillmentStatus status) {
        Fulfillment fulfillment = new Fulfillment(orderId, status, null, null, this);
        // The bidirectional relationship is maintained in the Fulfillment constructor
        return fulfillment;
    }
}
