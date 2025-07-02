package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

/** Resource representation of an Order.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */

public record OrderResource(
        UUID id,
        UUID userId,
        List<ItemResource> items
) {}
