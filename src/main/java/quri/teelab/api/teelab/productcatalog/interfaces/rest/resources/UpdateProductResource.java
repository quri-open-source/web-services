package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Resource for updating a Product.
 * Uses primitive types at the API boundary as part of the anti-corruption layer pattern.
 * Only includes fields that can be updated.
 * Note: Currency cannot be changed after product creation.
 */
public record UpdateProductResource(
        BigDecimal priceAmount,
        String status  // Will be converted to ProductStatus enum
) {
    // No validation in constructor since all fields are optional for PATCH
}
