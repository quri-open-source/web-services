package quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.ProductLike;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.UserId;

import java.util.Optional;
import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLike, UUID> {
    
    /**
     * Find a like by product ID and user ID
     */
    Optional<ProductLike> findByProductIdAndUserId(UUID productId, UserId userId);
    
    /**
     * Check if a user has liked a specific product
     */
    boolean existsByProductIdAndUserId(UUID productId, UserId userId);
    
    /**
     * Count total likes for a product
     */
    Long countByProductId(UUID productId);
    
    /**
     * Delete a like by product ID and user ID
     */
    void deleteByProductIdAndUserId(UUID productId, UserId userId);
}
