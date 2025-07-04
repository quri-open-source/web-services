package quri.teelab.api.teelab.orderprocessing.interfaces.acl;

import java.util.UUID;

public record ItemDto(
        UUID id,
        UUID orderId,
        UUID projectId,
        int quantity
) {}