package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import java.util.UUID;

/**
 * Resource representation of a Discount.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */

public record DiscountResource(
        UUID id
) {
}
