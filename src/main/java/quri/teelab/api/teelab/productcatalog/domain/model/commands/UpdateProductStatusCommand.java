package quri.teelab.api.teelab.productcatalog.domain.model.commands;

import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;

import java.util.UUID;

public record UpdateProductStatusCommand(
        UUID productId,
        ProductStatus status
) {
    public UpdateProductStatusCommand {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
}
