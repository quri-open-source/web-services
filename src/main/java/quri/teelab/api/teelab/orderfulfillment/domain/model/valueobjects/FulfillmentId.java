package quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record FulfillmentId(
    @Column(name = "id")
    UUID value
) implements Serializable {
    public FulfillmentId {
        if (value == null) {
            throw new IllegalArgumentException("Fulfillment ID cannot be null");
        }
    }
    
    public static FulfillmentId newId() {
        return new FulfillmentId(UUID.randomUUID());
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
