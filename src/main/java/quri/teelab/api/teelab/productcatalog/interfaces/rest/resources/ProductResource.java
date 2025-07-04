package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Resource representation of a Product.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */
public record ProductResource(
        UUID id,
        UUID projectId,
        BigDecimal priceAmount,
        String priceCurrency,
        String status,
        String projectTitle,
        String projectPreviewUrl,
        UUID projectUserId,
        String garmentSize,
        String garmentGender,
        String garmentColor,
        Long likeCount,
        Date createdAt,
        Date updatedAt
) {
}
