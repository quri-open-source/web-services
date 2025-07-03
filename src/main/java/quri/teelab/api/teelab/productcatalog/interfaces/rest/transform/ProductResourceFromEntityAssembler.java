package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductResource;

import java.util.stream.Collectors;

/**
 * Transforms Product domain entities to ProductResource DTOs.
 * Part of the anti-corruption layer for the REST interface.
 */
public class ProductResourceFromEntityAssembler {

    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId().toString(),
                entity.getProjectId().value(),
                entity.getManufacturerId().value(),
                entity.getPrice().amount(),
                entity.getPrice().currency().getCurrencyCode(),
                entity.getLikes(),
                entity.getTags(),
                entity.getCreatedAt(),
                entity.getGallery(),
                entity.getRating(),
                entity.getStatus()
        );
    }
}
