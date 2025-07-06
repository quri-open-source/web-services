package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductLikeStatusResource;

import java.util.UUID;

public class ProductLikeStatusResourceAssembler {
    
    public static ProductLikeStatusResource toResourceFromData(String message, UUID productId, UUID userId) {
        return new ProductLikeStatusResource(message, productId, userId);
    }
    
    public static ProductLikeStatusResource toLikeSuccessResource(UUID productId, UUID userId) {
        return toResourceFromData("Product liked successfully", productId, userId);
    }
    
    public static ProductLikeStatusResource toAlreadyLikedResource(UUID productId, UUID userId) {
        return toResourceFromData("Product already liked by user", productId, userId);
    }
}
