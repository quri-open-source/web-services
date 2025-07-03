package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;

/**
 * Resource for creating a new Product.
 * Uses primitive types at the API boundary as part of the anti-corruption layer pattern.
 */
public record CreateProductResource(
        String projectId,
        String userId,  // Changed from manufacturerId to userId
        BigDecimal price,
        String currency,
        List<String> tags,
        List<String> gallery,
        String status
) {
}
