package quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record FulfillmentItemId(UUID value) implements Serializable {
    public FulfillmentItemId {
        if (value == null) {
            throw new IllegalArgumentException("FulfillmentItemId cannot be null");
        }
    }

    public static FulfillmentItemId newId() {
        return new FulfillmentItemId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
