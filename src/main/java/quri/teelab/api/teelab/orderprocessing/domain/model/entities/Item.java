package quri.teelab.api.teelab.orderprocessing.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID productId;
    private int quantity;

    protected Item() { }

    public Item(UUID id,
                UUID productId,
                int quantity) {
        if (id != null) {
            this.id = id;
        }
        // If id is null, Hibernate will generate it automatically
        this.productId  = Objects.requireNonNull(productId,  "productId cannot be null");
        this.quantity   = quantity;
    }

    public UUID getId()          { return id; }
    public UUID getProductId()   { return productId; }
    public int getQuantity()     { return quantity; }
}