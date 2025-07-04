package quri.teelab.api.teelab.designlab.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.entities.Layer;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.*;

import java.util.*;

@Entity
@Getter
@Table(name = "projects")
public class Project {
    @EmbeddedId
    private ProjectId id;

    @Embedded
    private UserId userId;

    @Enumerated(EnumType.STRING)
    private GarmentColor garmentColor;

    @Enumerated(EnumType.STRING)
    private GarmentSize garmentSize;

    @Enumerated(EnumType.STRING)
    private GarmentGender garmentGender;

    private String title;

    @URL
    private String previewUrl;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private List<Layer> layers = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Default constructor required by JPA
    protected Project() {
    }

    public Project(ProjectId id, UserId userId, String title, String previewUrl, ProjectStatus status, GarmentColor garmentColor, GarmentSize garmentSize, GarmentGender garmentGender) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.previewUrl = previewUrl;
        this.status = status;
        this.layers = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.garmentColor = garmentColor;
        this.garmentSize = garmentSize;
        this.garmentGender = garmentGender;
    }

    public Project(CreateProjectCommand command) {
        this.id = new ProjectId(UUID.randomUUID());
        this.userId = command.userId();
        this.title = command.title();
        this.previewUrl = null; // Default value, can be set later
        this.status = ProjectStatus.BLUEPRINT; // Default status
        this.layers = new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.garmentColor = command.garmentColor();
        this.garmentSize = command.garmentSize();
        this.garmentGender = command.garmentGender();
    }

    public List<Layer> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    public void addLayer(Layer layer) {
        if (hasLayerWithId(layer.getId())) {
            throw new IllegalArgumentException("Layer with ID " + layer.getId() + " already exists in project");
        }
        layers.add(layer);
    }

    public void removeLayer(LayerId layerId) {
        layers.removeIf(layer -> layer.getId().equals(layerId));
    }

    public boolean hasLayerWithId(LayerId layerId) {
        return layers.stream().anyMatch(layer -> layer.getId().equals(layerId));
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
    public void setGarmentColor(GarmentColor garmentColor) {
        this.garmentColor = garmentColor;
    }
    public void setGarmentSize(GarmentSize garmentSize) {
        this.garmentSize = garmentSize;
    }
    public void setGarmentGender(GarmentGender garmentGender) {
        this.garmentGender = garmentGender;
    }

}
