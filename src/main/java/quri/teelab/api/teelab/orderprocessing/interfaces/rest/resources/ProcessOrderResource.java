package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProcessOrderResource(
        @NotNull UUID orderId,
        @NotNull String paymentMethod
) {}
