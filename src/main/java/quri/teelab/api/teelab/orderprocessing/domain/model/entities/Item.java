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

    private UUID projectId;
    private int quantity;

    protected Item() { }

    public Item(UUID id,
                UUID projectId,
                int quantity) {
        this.id         = id;
        this.projectId  = Objects.requireNonNull(projectId,  "projectId cannot be null");
        this.quantity   = quantity;
    }

    public UUID getId()          { return id; }
    public UUID getProjectId()   { return projectId; }
    public int getQuantity()     { return quantity; }
}