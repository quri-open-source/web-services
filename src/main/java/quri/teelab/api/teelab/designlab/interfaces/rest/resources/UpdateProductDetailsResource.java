package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

public record UpdateProductDetailsResource(
    String previewUrl,
    String status,
    String garmentColor,
    String garmentSize,
    String garmentGender
) {
    public UpdateProductDetailsResource {

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        if (garmentColor == null || garmentColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment color cannot be null or empty");
        }
        if (garmentSize == null || garmentSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment size cannot be null or empty");
        }
        if (garmentGender == null || garmentGender.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment gender cannot be null or empty");
        }
    }
}
