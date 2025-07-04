package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateTextLayerDetailsCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.UpdateTextLayerDetailsResource;

public class UpdateTextLayerDetailsCommandFromResourceAssembler {

    public static UpdateTextLayerDetailsCommand toCommandFromResource(
            UpdateTextLayerDetailsResource resource,
            String projectId,
            String layerId) {
        
        return new UpdateTextLayerDetailsCommand(
                ProjectId.of(projectId),
                LayerId.of(layerId),
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
