package quri.teelab.api.teelab.orderprocessing.interfaces.acl;

import java.util.UUID;

/**
 * Data Transfer Object for Items from the Order Processing bounded context.
 * Used by the Anti-Corruption Layer (ACL) to expose item data to other bounded contexts.
 */
public record ItemDto(
        UUID id,
        UUID orderId,
        UUID productId,
        int quantity
) {}
