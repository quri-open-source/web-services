package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentColor;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentGender;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentSize;

import java.util.UUID;

/**
 * Resource representing project details for product catalog integration.
 * Contains essential project information needed by the product catalog bounded context.
 */
public record ProjectDetailsResource(
        UUID projectId,
        String title,
        UUID userId,
        String previewUrl,
        GarmentSize size,
        GarmentGender gender,
        GarmentColor color
) {
    public ProjectDetailsResource {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
    }
}
