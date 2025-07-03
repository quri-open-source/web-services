package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Resource representation of a Product.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */
public record ProductResource(
        String id,
        String projectId,
        String manufacturerId,
        BigDecimal price,
        String currency,
        Integer likes,
        List<String> tags,
        Date createdAt,
        List<String> gallery,
        Double rating,
        String status
) {
}
