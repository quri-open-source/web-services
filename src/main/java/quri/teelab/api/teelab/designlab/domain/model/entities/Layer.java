package quri.teelab.api.teelab.designlab.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerType;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "layer_type", discriminatorType = DiscriminatorType.STRING)
@Getter
public abstract class Layer {
    private final int DEFAULT_X_POSITION = 0;
    private final int DEFAULT_Y_POSITION = 0;
    private final int DEFAULT_Z_INDEX = 0;
    private final float DEFAULT_OPACITY = 1;

    @EmbeddedId
    private LayerId id;

    @Column(nullable = false)
    private int x;
    @Column(nullable = false)
    private int y;
    @Column(nullable = false)
    private int z;

    @Column(nullable = false)
    private Float opacity;
    @Column(nullable = false)
    private boolean isVisible;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LayerType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    protected Layer() {}

    public Layer(LayerId id, LayerType type) {
        this.id = id;
        this.x = DEFAULT_X_POSITION;
        this.y = DEFAULT_Y_POSITION;
        this.z = DEFAULT_Z_INDEX;
        this.opacity = DEFAULT_OPACITY;
        this.isVisible = true;
        this.type = type;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public void updateCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.updatedAt = new Date();
    }

}
