package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.CreateProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentColor;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentGender;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentSize;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateProjectResource;

import java.util.UUID;

public class CreateProjectCommandFromResourceAssembler {
    public static CreateProjectCommand toCommandFromResource(CreateProjectResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateProjectResource cannot be null");
        }

        return new CreateProjectCommand(
                new UserId(UUID.fromString(resource.userId())),
                resource.title(),
                GarmentColor.valueOf(resource.garmentColor().toUpperCase()),
                GarmentGender.valueOf(resource.garmentGender().toUpperCase()),
                GarmentSize.valueOf(resource.garmentSize().toUpperCase())
        );
    }
}
