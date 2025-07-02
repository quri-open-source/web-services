package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateImageLayerResource;

public class CreateImageLayerCommandFromResourceAssembler {

    public static CreateImageLayerCommand toCommandFromResource(CreateImageLayerResource resource, String projectId) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateImageLayerResource cannot be null");
        }
        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("Project ID cannot be null or blank");
        }
        if (resource.imageUrl() == null || resource.imageUrl().isBlank()) {
            throw new IllegalArgumentException("Image URL cannot be null or blank");
        }
        if (resource.width() == null || resource.width().isBlank()) {
            throw new IllegalArgumentException("Width cannot be null or blank");
        }
        if (resource.height() == null || resource.height().isBlank()) {
            throw new IllegalArgumentException("Height cannot be null or blank");
        }
        
        try {
            // Parse width and height to float
            float width = Float.parseFloat(resource.width());
            float height = Float.parseFloat(resource.height());
            
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("Width and height must be positive numbers");
            }
            
            return new CreateImageLayerCommand(
                ProjectId.of(projectId),
                resource.imageUrl(),
                width,
                height
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Width and height must be valid numbers", e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error processing image layer", e);
        }
    }
}
