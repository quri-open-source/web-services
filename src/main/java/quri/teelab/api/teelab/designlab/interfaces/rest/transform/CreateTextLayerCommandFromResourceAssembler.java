package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.CreateTextLayerCommand;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateTextLayerResource;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public class CreateTextLayerCommandFromResourceAssembler {
    
    public static CreateTextLayerCommand toCommandFromResource(CreateTextLayerResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("CreateTextLayerResource cannot be null");
        }
        
        return new CreateTextLayerCommand(
                ProjectId.of(resource.projectId()),
                resource.text(),
                resource.fontColor(),
                resource.fontFamily(),
                resource.fontSize(),
                resource.isBold(),
                resource.isItalic(),
                resource.isUnderlined()
        );
    }
}
