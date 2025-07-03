package quri.teelab.api.teelab.designlab.application.internal.acl;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetAllProjectsByUserIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetLayerByIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectByIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectDetailsForProductQuery;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.designlab.domain.services.LayerQueryService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectQueryService;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.ProjectRepository;
import quri.teelab.api.teelab.designlab.interfaces.acl.ProjectContextFacade;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the Project Context Facade.
 * Acts as an Anti-Corruption Layer for the Design Lab bounded context.
 * This implementation coordinates between domain services and repositories
 * to provide clean access to design lab information, exposing only IDs.
 */
@Service
public class ProjectContextFacadeImpl implements ProjectContextFacade {
    
    private final ProjectQueryService projectQueryService;
    private final LayerQueryService layerQueryService;
    private final ProjectRepository projectRepository;
    
    public ProjectContextFacadeImpl(
            ProjectQueryService projectQueryService, 
            LayerQueryService layerQueryService,
            ProjectRepository projectRepository) {
        this.projectQueryService = projectQueryService;
        this.layerQueryService = layerQueryService;
        this.projectRepository = projectRepository;
    }
    
    @Override
    public UUID fetchProjectIdById(UUID projectId) {
        try {
            var query = new GetProjectByIdQuery(ProjectId.of(projectId.toString()));
            var project = projectQueryService.handle(query);
            return project.getId().projectId();
        } catch (IllegalArgumentException e) {
            // Project not found
            return null;
        }
    }
    
    @Override
    public List<UUID> fetchProjectIdsByUserId(UUID userId) {
        try {
            var query = new GetAllProjectsByUserIdQuery(UserId.of(userId.toString()));
            var projects = projectQueryService.handle(query);
            return projects.stream()
                    .map(project -> project.getId().projectId())
                    .toList();
        } catch (IllegalArgumentException e) {
            // No projects found for user
            return List.of();
        }
    }
    
    @Override
    public UUID fetchLayerIdById(UUID layerId) {
        try {
            var query = new GetLayerByIdQuery(LayerId.of(layerId.toString()));
            var layer = layerQueryService.handle(query);
            return layer.getId().layerId();
        } catch (IllegalArgumentException e) {
            // Layer not found
            return null;
        }
    }
    
    @Override
    public boolean projectExists(UUID projectId) {
        return projectRepository.existsById(ProjectId.of(projectId.toString()));
    }
    
    @Override
    public boolean userHasProjects(UUID userId) {
        return projectRepository.existsByUserId(UserId.of(userId.toString()));
    }
    
    @Override
    public long getProjectCountByUserId(UUID userId) {
        return projectRepository.countByUserId(UserId.of(userId.toString()));
    }
    
    @Override
    public long getLayerCountByProjectId(UUID projectId) {
        try {
            var query = new GetProjectByIdQuery(ProjectId.of(projectId.toString()));
            var project = projectQueryService.handle(query);
            return project.getLayers().size();
        } catch (IllegalArgumentException e) {
            // Project not found
            return 0L;
        }
    }

    @Override
    public ProjectContextFacade.ProjectDetailsInfo fetchProjectDetailsForProduct(UUID projectId) {
        try {
            var query = new GetProjectDetailsForProductQuery(ProjectId.of(projectId.toString()));
            var project = projectQueryService.handle(query);
            
            return new ProjectContextFacade.ProjectDetailsInfo(
                    project.getId().projectId(),
                    project.getTitle(),
                    project.getUserId().userId(),
                    project.getPreviewUrl()
            );
        } catch (IllegalArgumentException e) {
            // Project not found
            return null;
        }
    }
}
