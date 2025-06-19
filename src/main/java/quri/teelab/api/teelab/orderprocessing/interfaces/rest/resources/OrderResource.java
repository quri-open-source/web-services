package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/** Resource representation of an Order.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */

public record OrderResource(
        UUID id,
        UUID userId,
        Date createdAt,
        String status,
        BigDecimal totalAmount,
        String transactionId,
        String description,
        AddressResource shippingAddress,
        List<DiscountResource> appliedDiscounts,
        List<ItemResource> items
) {
}
