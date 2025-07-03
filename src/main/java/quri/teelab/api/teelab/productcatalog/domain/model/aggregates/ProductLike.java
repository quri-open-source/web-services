package quri.teelab.api.teelab.productcatalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.UUID;

/**
 * ProductLike aggregate root representing a user's like on a product.
 * Ensures one like per user per product through database constraints.
 */
@Getter
@Entity
@Table(name = "product_likes", 
       uniqueConstraints = @UniqueConstraint(
           name = "uk_product_user_like", 
           columnNames = {"product_id", "user_id"}
       ))
public class ProductLike extends AuditableAbstractAggregateRoot<ProductLike> {
    
    @Column(name = "product_id", nullable = false)
    private UUID productId;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    public ProductLike() {
        // Default constructor for JPA
    }

    public ProductLike(UUID productId, UUID userId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        this.productId = productId;
        this.userId = UserId.of(userId);
    }
}
