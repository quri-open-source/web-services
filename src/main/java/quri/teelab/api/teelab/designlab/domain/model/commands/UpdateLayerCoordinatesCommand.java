package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record UpdateLayerCoordinatesCommand(
        ProjectId projectId,
        LayerId layerId,
        Integer x,
        Integer y,
        Integer z
) {
    public UpdateLayerCoordinatesCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (layerId == null) {
            throw new IllegalArgumentException("Layer ID cannot be null");
        }
        if (x == null) {
            throw new IllegalArgumentException("X coordinate cannot be null");
        }
        if (y == null) {
            throw new IllegalArgumentException("Y coordinate cannot be null");
        }
        if (z == null) {
            throw new IllegalArgumentException("Z coordinate cannot be null");
        }
    }
}
