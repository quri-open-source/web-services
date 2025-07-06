package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.aggregates.Project;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.ProjectDetailsResource;

/**
 * Assembler for transforming Project domain entities to ProjectDetailsResource DTOs.
 * Focuses on essential project information needed for product catalog integration.
 */
public class ProjectDetailsResourceFromEntityAssembler {

    public static ProjectDetailsResource toResourceFromEntity(Project entity) {
        return new ProjectDetailsResource(
                entity.getId().projectId(),
                entity.getTitle(),
                entity.getUserId().userId(),
                entity.getPreviewUrl(),
                entity.getGarmentSize(),
                entity.getGarmentGender(),
                entity.getGarmentColor()
        );
    }
}
