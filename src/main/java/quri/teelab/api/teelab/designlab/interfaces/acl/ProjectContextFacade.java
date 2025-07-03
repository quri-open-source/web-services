package quri.teelab.api.teelab.designlab.interfaces.acl;

import java.util.List;
import java.util.UUID;

/**
 * Anti-Corruption Layer facade for the Design Lab bounded context.
 * Provides access to design lab domain information for other bounded contexts.
 * Following DDD principles, this facade exposes only IDs and essential operations
 * without transmitting complete domain objects.
 */
public interface ProjectContextFacade {
    
    /**
     * Retrieves the project ID if the project exists.
     * @param projectId The unique identifier of the project
     * @return The project ID if found, null otherwise
     */
    UUID fetchProjectIdById(UUID projectId);
    
    /**
     * Retrieves all project IDs belonging to a specific user.
     * @param userId The unique identifier of the user
     * @return List of project IDs belonging to the user, empty list if none found
     */
    List<UUID> fetchProjectIdsByUserId(UUID userId);
    
    /**
     * Retrieves the layer ID if the layer exists.
     * @param layerId The unique identifier of the layer
     * @return The layer ID if found, null otherwise
     */
    UUID fetchLayerIdById(UUID layerId);
    
    /**
     * Checks if a project exists in the system.
     * @param projectId The unique identifier of the project
     * @return true if project exists, false otherwise
     */
    boolean projectExists(UUID projectId);
    
    /**
     * Checks if a user has any projects in the system.
     * @param userId The unique identifier of the user
     * @return true if user has projects, false otherwise
     */
    boolean userHasProjects(UUID userId);
    
    /**
     * Gets the total number of projects for a specific user.
     * @param userId The unique identifier of the user
     * @return The count of projects
     */
    long getProjectCountByUserId(UUID userId);
    
    /**
     * Gets the total number of layers in a specific project.
     * @param projectId The unique identifier of the project
     * @return The count of layers in the project
     */
    long getLayerCountByProjectId(UUID projectId);

    /**
     * Retrieves essential project details for product catalog integration.
     * @param projectId The unique identifier of the project
     * @return ProjectDetailsInfo containing title, userId, projectId, and previewUrl; null if project not found
     */
    ProjectDetailsInfo fetchProjectDetailsForProduct(UUID projectId);

    /**
     * Data transfer object for project details needed by product catalog.
     */
    record ProjectDetailsInfo(
            UUID projectId,
            String title,
            UUID userId,
            String previewUrl
    ) {}
}
