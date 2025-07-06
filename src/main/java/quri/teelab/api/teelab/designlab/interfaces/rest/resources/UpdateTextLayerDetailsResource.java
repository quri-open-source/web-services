package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

public record UpdateTextLayerDetailsResource(
        String text,
        String fontColor,
        String fontFamily,
        Integer fontSize,
        Boolean isBold,
        Boolean isItalic,
        Boolean isUnderlined
) {
    public UpdateTextLayerDetailsResource {
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
