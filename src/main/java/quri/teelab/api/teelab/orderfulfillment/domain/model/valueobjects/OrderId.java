package quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record OrderId(
    @Column(name = "order_id") 
    UUID value
) implements Serializable {
    public OrderId {
        if (value == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
