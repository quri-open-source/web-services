package quri.teelab.api.teelab.designlab.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateTextLayerCommand;
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
}
