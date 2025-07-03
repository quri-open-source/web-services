package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import java.util.UUID;

/**
 * Resource representation of an Item.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */

public record ItemResource(
    UUID id,
    UUID productId,
    int quantity

) {}
