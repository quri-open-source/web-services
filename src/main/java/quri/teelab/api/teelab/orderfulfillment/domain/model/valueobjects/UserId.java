package quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record UserId(
    @Column(name = "user_id") 
    UUID value
) implements Serializable {
    public UserId {
        if (value == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
