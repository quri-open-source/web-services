package quri.teelab.api.teelab.designlab.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.domain.model.commands.*;
import quri.teelab.api.teelab.designlab.domain.model.entities.ImageLayer;
import quri.teelab.api.teelab.designlab.domain.model.entities.TextLayer;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.services.LayerCommandService;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.LayerRepository;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.ProjectRepository;

@Service
public class LayerCommandServiceImpl implements LayerCommandService {
    private final ProjectRepository projectRepository;

    public LayerCommandServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public LayerId handle(CreateTextLayerCommand command) {
        var project = projectRepository
                .findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist."));

        var layer = new TextLayer(command);
        project.addLayer(layer);
        projectRepository.save(project);
        return layer.getId();
    }

    @Override
    public LayerId handle(CreateImageLayerCommand command) {
        var project = projectRepository
                .findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist."));

        // Validate image URL
        String imageUrl = command.imageUrl();
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty.");
        }

        // Basic URL validation
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            throw new IllegalArgumentException("Image URL must be a valid HTTP or HTTPS URL.");
        }

        var layer = new ImageLayer(command, imageUrl);

        project.addLayer(layer);
        projectRepository.save(project);
        return layer.getId();
    }

    @Override
    public LayerId handle(UpdateLayerCoordinatesCommand command) {
        // Verify that the project exists
        var project = projectRepository
                .findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist."));

        // Find the layer in the project
        var layer = project.getLayers().stream()
                .filter(l -> l.getId().equals(command.layerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Layer with ID " + command.layerId() + " does not exist in project " + command.projectId()));

        // Update the coordinates
        layer.updateCoordinates(command.x(), command.y(), command.z());

        // Save the project (which will cascade to save the layer)
        projectRepository.save(project);

        return layer.getId();
    }

    @Override
    public LayerId handle(UpdateTextLayerDetailsCommand command) {
        // Verify that the project exists
        var project = projectRepository
                .findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist."));

        // Find the text layer in the project
        var layer = project.getLayers().stream()
                .filter(l -> l.getId().equals(command.layerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Layer with ID " + command.layerId() + " does not exist in project " + command.projectId()));

        // Verify that the layer is a text layer
        if (!(layer instanceof TextLayer)) {
            throw new IllegalArgumentException("Layer with ID " + command.layerId() + " is not a text layer");
        }

        TextLayer textLayer = (TextLayer) layer;

        // Update the text layer details
        textLayer.updateDetails(
                command.text(),
                command.fontColor(),
                command.fontFamily(),
                command.fontSize(),
                command.isBold(),
                command.isItalic(),
                command.isUnderlined()
        );

        // Save the project (which will cascade to save the layer)
        projectRepository.save(project);

        return layer.getId();
    }

    @Override
    public LayerId handle(UpdateImageLayerDetailsCommand command) {
        // Verify that the project exists
        var project = projectRepository
                .findById(command.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist."));

        // Find the image layer in the project
        var layer = project.getLayers().stream()
                .filter(l -> l.getId().equals(command.layerId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Layer with ID " + command.layerId() + " does not exist in project " + command.projectId()));

        // Verify that the layer is an image layer
        if (!(layer instanceof ImageLayer)) {
            throw new IllegalArgumentException("Layer with ID " + command.layerId() + " is not an image layer");
        }

        ImageLayer imageLayer = (ImageLayer) layer;

        // Validate image URL
        String imageUrl = command.imageUrl();
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            throw new IllegalArgumentException("Image URL must be a valid HTTP or HTTPS URL.");
        }

        // Update the image layer details
        imageLayer.updateDetails(
                command.imageUrl(),
                command.width(),
                command.height()
        );

        // Save the project (which will cascade to save the layer)
        projectRepository.save(project);

        return layer.getId();
    }
}
