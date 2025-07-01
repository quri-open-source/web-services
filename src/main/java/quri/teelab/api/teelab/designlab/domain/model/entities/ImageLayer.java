package quri.teelab.api.teelab.designlab.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerType;

import java.awt.*;
import java.util.UUID;

@Entity
@DiscriminatorValue("IMAGE")
@Getter
public class ImageLayer extends Layer {
    @URL
    @Column(nullable = true)
    private String imageUrl;
    @Column(nullable = true)
    private Float width;
    @Column(nullable = true)
    private Float height;

    protected ImageLayer() {
    }

    public ImageLayer(LayerId id, LayerType type, String imageUrl, Float width, Float height) {
        super(id, type);
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
    }

    public ImageLayer(CreateImageLayerCommand command, String imageUrl) {
        super(new LayerId(UUID.randomUUID()), LayerType.IMAGE);
        this.imageUrl = imageUrl;
        this.width = command.width();
        this.height = command.height();
    }
}
