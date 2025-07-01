package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

import org.springframework.web.multipart.MultipartFile;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record CreateImageLayerResource
        (ProjectId projectId, MultipartFile imageUrl, Float width, Float height)
{

    public CreateImageLayerResource {
        if (width == null || width <= 0) {
            throw new IllegalArgumentException("Width must be a positive number");
        }
        if (height == null || height <= 0) {
            throw new IllegalArgumentException("Height must be a positive number");
        }
    }
}
