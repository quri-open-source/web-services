package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductUserLikeStatusResource;

public class ProductUserLikeStatusResourceAssembler {
    
    public static ProductUserLikeStatusResource toResourceFromStatus(Boolean isLiked) {
        // Ensure non-null status
        Boolean safeStatus = (isLiked != null) ? isLiked : false;
        return new ProductUserLikeStatusResource(safeStatus);
    }
}
