package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

public record UpdateImageLayerDetailsResource(
        String imageUrl,
        String width,
        String height
) {
    public UpdateImageLayerDetailsResource {
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
