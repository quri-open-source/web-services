package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentColor;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentGender;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.GarmentSize;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectStatus;

public record UpdateProductDetailsCommand(
    ProjectId projectId,
    String previewUrl,
    ProjectStatus status,
    GarmentColor garmentColor,
    GarmentSize garmentSize,
    GarmentGender garmentGender
) {
    public UpdateProductDetailsCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("ProjectId cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Project status cannot be null");
        }
        if (garmentColor == null) {
            throw new IllegalArgumentException("Garment color cannot be null");
        }
        if (garmentSize == null) {
            throw new IllegalArgumentException("Garment size cannot be null");
        }
        if (garmentGender == null) {
            throw new IllegalArgumentException("Garment gender cannot be null");
        }
    }
}
