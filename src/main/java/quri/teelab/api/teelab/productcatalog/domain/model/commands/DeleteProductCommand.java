package quri.teelab.api.teelab.productcatalog.domain.model.commands;

import java.util.UUID;

public record DeleteProductCommand(
        UUID productId
) {
    public DeleteProductCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
    }
}
