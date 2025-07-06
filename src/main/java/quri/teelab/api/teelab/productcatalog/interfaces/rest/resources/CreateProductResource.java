package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.math.BigDecimal;

/**
 * Resource for creating a new Product.
 * Uses primitive types at the API boundary as part of the anti-corruption layer pattern.
 */
public record CreateProductResource(
        String projectId,
        BigDecimal priceAmount,
        String priceCurrency
) {
    public CreateProductResource {
        if (projectId == null || projectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Project ID cannot be null or empty");
        }
        if (priceAmount == null) {
            throw new IllegalArgumentException("Price amount cannot be null");
        }
        if (priceCurrency == null || priceCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("Price currency cannot be null or empty");
        }
        // Status is optional, will default to AVAILABLE if not provided
    }
}
