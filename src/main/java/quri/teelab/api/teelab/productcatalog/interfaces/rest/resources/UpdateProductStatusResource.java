package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

/**
 * Resource for updating a Product's status.
 * Uses primitive types at the API boundary as part of the anti-corruption layer pattern.
 */
public record UpdateProductStatusResource(
        String status  // Will be converted to ProductStatus enum
) {
    public UpdateProductStatusResource {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
