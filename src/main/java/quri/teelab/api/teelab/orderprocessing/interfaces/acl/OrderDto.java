package quri.teelab.api.teelab.orderprocessing.interfaces.acl;

import java.util.UUID;

public record OrderDto(
        UUID id,
        UUID userId,
        UUID productId,
        int quantity,
        UUID itemId
) {}
