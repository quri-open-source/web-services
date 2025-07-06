package quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record ManufacturerId(
    @Column(name = "manufacturer_id") 
    UUID value
) implements Serializable {
    public ManufacturerId {
        if (value == null) {
            throw new IllegalArgumentException("Manufacturer ID cannot be null");
        }
    }
    
    public static ManufacturerId newId() {
        return new ManufacturerId(UUID.randomUUID());
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
