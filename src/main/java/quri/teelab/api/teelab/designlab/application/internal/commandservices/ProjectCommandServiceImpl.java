package quri.teelab.api.teelab.designlab.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.domain.model.aggregates.Project;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateTextLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.DeleteProjectLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateProductDetailsCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.LayerRepository;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.ProjectRepository;

import java.util.UUID;

@Service
public class ProjectCommandServiceImpl implements ProjectCommandService {
    private final ProjectRepository projectRepository;
    private final LayerRepository layerRepository;

    public ProjectCommandServiceImpl(ProjectRepository projectRepository, LayerRepository layerRepository) {
        this.projectRepository = projectRepository;
        this.layerRepository = layerRepository;
    }

    @Override
    @Transactional
    public LayerId handle(DeleteProjectLayerCommand command) {
        var result = projectRepository.findById(command.projectId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist.");
        }
        var project = result.get();
        if (!project.hasLayerWithId(command.layerId())) {
            throw new IllegalArgumentException("Project with ID " + command.projectId() + " does not have a layer with ID " + command.layerId() + ".");
        }
        project.removeLayer(command.layerId());
        projectRepository.save(project);
        return command.layerId();
    }

    @Override
    @Transactional
    public ProjectId handle(CreateProjectCommand command) {
        // Here should be a validation for the user id

        var project = new Project(command);

        try {
            projectRepository.save(project);
            return project.getId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create project", e);
        }

    }

    @Override
    @Transactional
    public ProjectId handle(UpdateProductDetailsCommand command) {
        var result = projectRepository.findById(command.projectId());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Project with ID " + command.projectId() + " does not exist.");
        }
        var project = result.get();
        // Actualizar los detalles del proyecto
        project.setPreviewUrl(command.previewUrl());
        project.setStatus(command.status());
        project.setGarmentColor(command.garmentColor());
        project.setGarmentSize(command.garmentSize());
        project.setGarmentGender(command.garmentGender());
        projectRepository.save(project);
        return project.getId();
    }
}
