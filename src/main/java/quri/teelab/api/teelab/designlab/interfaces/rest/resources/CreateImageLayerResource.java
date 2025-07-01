package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;

public record CreateImageLayerResource(
        MultipartFile imageFile
) {
    public CreateImageLayerResource {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }
        
        // Validate file type
        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
        
        // Validate file size (e.g., max 10MB)
        if (imageFile.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("Image file size cannot exceed 10MB");
        }
    }
}
