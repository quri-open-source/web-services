package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import org.springframework.web.multipart.MultipartFile;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateImageLayerResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateImageLayerCommandFromResourceAssembler {

    public static CreateImageLayerCommand ToCommandFromResource(CreateImageLayerResource resource) {
        try {
            File imageFile = convertMultipartToFile(resource.imageUrl());
            return new CreateImageLayerCommand(resource.projectId(), imageFile, resource.width(), resource.height());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File convFile = File.createTempFile("upload", multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        }
        return convFile;
    }
}
