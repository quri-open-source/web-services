package quri.teelab.api.teelab.orderprocessing.domain.model.entities;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class Discount {
    private UUID id;

    protected Discount() { }

    public Discount(UUID id) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
    }

    public UUID getId() { return id; }
}
