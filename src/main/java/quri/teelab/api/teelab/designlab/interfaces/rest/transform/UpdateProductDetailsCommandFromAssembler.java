package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateProductDetailsCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentColor;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentGender;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentSize;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectStatus;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.UpdateProductDetailsResource;

public class UpdateProductDetailsCommandFromAssembler {
    public static UpdateProductDetailsCommand toCommandFromResource(UpdateProductDetailsResource resource, String projectId) {
        return new UpdateProductDetailsCommand(
            ProjectId.of(projectId),
            resource.previewUrl(),
            ProjectStatus.valueOf(resource.status()),
            GarmentColor.valueOf(resource.garmentColor()),
            GarmentSize.valueOf(resource.garmentSize()),
            GarmentGender.valueOf(resource.garmentGender())
        );
    }
}
