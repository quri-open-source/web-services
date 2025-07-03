package quri.teelab.api.teelab.productcatalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.*;

@Getter
@Entity
@Table(name = "products")
public class Product extends AuditableAbstractAggregateRoot<Product> {
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "project_id", nullable = false))
    private ProjectId projectId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency", length = 3, nullable = false))
    })
    private Money price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    // Project information from DesignLab (cached for performance)
    @Column(name = "project_title", nullable = false)
    private String projectTitle;

    @Column(name = "project_preview_url")
    private String projectPreviewUrl;

    @Column(name = "project_user_id", nullable = false)
    private UUID projectUserId;

    // Like count (cached for performance)
    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L;

    public Product() {
        // Default constructor for JPA
    }

    public Product(CreateProductCommand command) {
        this.projectId = command.projectId();
        this.price = command.price();
        this.status = command.status();
        this.projectTitle = command.projectTitle();
        this.projectPreviewUrl = command.projectPreviewUrl();
        this.projectUserId = command.projectUserId();
    }

    public void updatePrice(Money newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.price = newPrice;
    }

    public void updateStatus(ProductStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = newStatus;
    }

    public void updateProjectInfo(String title, String previewUrl, UUID userId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Project title cannot be null or empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("Project user ID cannot be null");
        }
        this.projectTitle = title;
        this.projectPreviewUrl = previewUrl;
        this.projectUserId = userId;
    }

    public void updateLikeCount(Long count) {
        if (count == null || count < 0) {
            throw new IllegalArgumentException("Like count cannot be null or negative");
        }
        this.likeCount = count;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount = Math.max(0, this.likeCount - 1);
    }
}
