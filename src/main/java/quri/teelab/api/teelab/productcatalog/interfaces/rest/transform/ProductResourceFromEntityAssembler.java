package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductResource;

import java.util.UUID;

/**
 * Transforms Product domain entities to ProductResource DTOs.
 * Part of the anti-corruption layer for the REST interface.
 */
public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                UUID.fromString(entity.getProjectId().value()),
                entity.getPrice().amount(),
                entity.getPrice().currency().getCurrencyCode(),
                entity.getStatus().toString(),
                entity.getProjectTitle(),
                entity.getProjectPreviewUrl(),
                entity.getProjectUserId(),
                entity.getGarmentSize(),
                entity.getGarmentGender(),
                entity.getGarmentColor(),
                entity.getLikeCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
