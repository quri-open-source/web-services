package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record UpdateTextLayerDetailsCommand(
        ProjectId projectId,
        LayerId layerId,
        String text,
        String fontColor,
        String fontFamily,
        Integer fontSize,
        Boolean isBold,
        Boolean isItalic,
        Boolean isUnderlined
) {
    public UpdateTextLayerDetailsCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (layerId == null) {
            throw new IllegalArgumentException("Layer ID cannot be null");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        if (fontColor == null || fontColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Font color cannot be null or empty");
        }
        if (fontFamily == null || fontFamily.trim().isEmpty()) {
            throw new IllegalArgumentException("Font family cannot be null or empty");
        }
        if (fontSize == null || fontSize <= 0) {
            throw new IllegalArgumentException("Font size must be a positive number");
        }
        if (isBold == null) {
            throw new IllegalArgumentException("isBold cannot be null");
        }
        if (isItalic == null) {
            throw new IllegalArgumentException("isItalic cannot be null");
        }
        if (isUnderlined == null) {
            throw new IllegalArgumentException("isUnderlined cannot be null");
        }
    }
}
