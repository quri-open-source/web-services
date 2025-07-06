package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record UpdateImageLayerDetailsCommand(
        ProjectId projectId,
        LayerId layerId,
        String imageUrl,
        String width,
        String height
) {
    public UpdateImageLayerDetailsCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (layerId == null) {
            throw new IllegalArgumentException("Layer ID cannot be null");
        }
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }
        if (width == null || width.trim().isEmpty()) {
            throw new IllegalArgumentException("Width cannot be null or empty");
        }
        if (height == null || height.trim().isEmpty()) {
            throw new IllegalArgumentException("Height cannot be null or empty");
        }
        
        // Validate that width and height are numeric
        try {
            double widthValue = Double.parseDouble(width);
            double heightValue = Double.parseDouble(height);
            
            if (widthValue <= 0 || heightValue <= 0) {
                throw new IllegalArgumentException("Width and height must be positive numbers");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Width and height must be valid numbers");
        }
    }
}
