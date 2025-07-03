package quri.teelab.api.teelab.productcatalog.domain.services;

import quri.teelab.api.teelab.productcatalog.domain.model.commands.LikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UnlikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.CheckIfUserLikedProductQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetLikeCountByProductQuery;

public interface ProductLikeService {
    
    /**
     * Like a product
     */
    void handle(LikeProductCommand command);
    
    /**
     * Unlike a product
     */
    void handle(UnlikeProductCommand command);
    
    /**
     * Check if user liked a product
     */
    Boolean handle(CheckIfUserLikedProductQuery query);
    
    /**
     * Get like count for a product
     */
    Long handle(GetLikeCountByProductQuery query);
}
