package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateLayerCoordinatesCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.UpdateLayerCoordinatesResource;

public class UpdateLayerCoordinatesCommandFromResourceAssembler {

    public static UpdateLayerCoordinatesCommand toCommandFromResource(
            UpdateLayerCoordinatesResource resource, 
            String projectId, 
            String layerId) {
        
        return new UpdateLayerCoordinatesCommand(
                ProjectId.of(projectId),
                LayerId.of(layerId),
                resource.x(),
                resource.y(),
                resource.z()
        );
    }
}
