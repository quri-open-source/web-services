package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import org.springframework.web.multipart.MultipartFile;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateImageLayerResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateImageLayerCommandFromResourceAssembler {

    public static CreateImageLayerCommand toCommandFromResource(MultipartFile imageFile, String projectId) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be null or empty");
        }
        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("Project ID cannot be null or blank");
        }
        
        try {
            // Calculate image dimensions
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
            if (bufferedImage == null) {
                throw new IllegalArgumentException("Invalid image file format");
            }
            
            float width = (float) bufferedImage.getWidth();
            float height = (float) bufferedImage.getHeight();
            
            // Convert MultipartFile to File for the command
            File tempImageFile = convertMultipartToFile(imageFile);
            
            return new CreateImageLayerCommand(
                ProjectId.of(projectId),
                tempImageFile,
                width,
                height
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to process image file", e);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error processing image layer", e);
        }
    }

    public static CreateImageLayerCommand toCommandFromResource(CreateImageLayerResource resource, String projectId) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateImageLayerResource cannot be null");
        }
        
        // Delegate to the main method that handles the MultipartFile directly
        return toCommandFromResource(resource.imageFile(), projectId);
    }

    private static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("MultipartFile cannot be null or empty");
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = "";
        
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        File tempFile = File.createTempFile("image_layer_", fileExtension);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        
        return tempFile;
    }
}
