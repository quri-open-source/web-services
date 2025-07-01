package quri.teelab.api.teelab.orderprocessing.domain.model.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Item {
    @Id
    private UUID id;

    private UUID projectId;
    private int quantity;
    private BigDecimal unitPrice;

    protected Item() { }

    public Item(UUID id,
                UUID projectId,
                int quantity,
                BigDecimal unitPrice) {
        this.id         = Objects.requireNonNull(id,         "id cannot be null");
        this.projectId  = Objects.requireNonNull(projectId,  "projectId cannot be null");
        this.quantity   = quantity;
        this.unitPrice  = Objects.requireNonNull(unitPrice,  "unitPrice cannot be null");
    }

    public UUID getId()          { return id; }
    public UUID getProjectId()   { return projectId; }
    public int getQuantity()     { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
}