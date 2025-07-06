package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductLikeCountResource;

public class ProductLikeCountResourceAssembler {
    
    public static ProductLikeCountResource toResourceFromCount(Long likeCount) {
        // Ensure non-null and non-negative count
        Long safeCount = (likeCount != null && likeCount >= 0) ? likeCount : 0L;
        return new ProductLikeCountResource(safeCount);
    }
}
