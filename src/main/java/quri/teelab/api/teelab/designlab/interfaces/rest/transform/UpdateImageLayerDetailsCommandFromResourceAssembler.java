package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateImageLayerDetailsCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.UpdateImageLayerDetailsResource;

public class UpdateImageLayerDetailsCommandFromResourceAssembler {

    public static UpdateImageLayerDetailsCommand toCommandFromResource(
            UpdateImageLayerDetailsResource resource,
            String projectId,
            String layerId) {
        
        return new UpdateImageLayerDetailsCommand(
                ProjectId.of(projectId),
                LayerId.of(layerId),
                resource.imageUrl(),
                resource.width(),
                resource.height()
        );
    }
}
