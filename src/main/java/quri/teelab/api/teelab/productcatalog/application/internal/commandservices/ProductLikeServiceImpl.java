package quri.teelab.api.teelab.productcatalog.application.internal.commandservices;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.ProductLike;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.LikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UnlikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.CheckIfUserLikedProductQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetLikeCountByProductQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductLikeService;
import quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories.ProductLikeRepository;
import quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories.ProductRepository;

import java.time.Instant;

@Service
@Transactional
public class ProductLikeServiceImpl implements ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductLikeServiceImpl(ProductLikeRepository productLikeRepository,
                                 ProductRepository productRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.productLikeRepository = productLikeRepository;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(LikeProductCommand command) {
        var userId = UserId.of(command.userId());

        // Check if product exists
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + command.productId()));

        // Check if already liked
        if (productLikeRepository.existsByProductIdAndUserId(command.productId(), userId)) {
            return; // Already liked, do nothing (idempotent)
        }

        // Create like
        var productLike = new ProductLike(command.productId(), command.userId());
        productLikeRepository.save(productLike);

        // Update cached like count
        product.incrementLikeCount();
        productRepository.save(product);

        // Publish domain event for analytics (Phase 3)
        var event = new ProductLikedEvent(command.productId(), command.userId(), Instant.now());
        eventPublisher.publishEvent(event);
    }

    @Override
    public void handle(UnlikeProductCommand command) {
        var userId = UserId.of(command.userId());

        // Check if product exists
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + command.productId()));

        // Check if like exists
        var existingLike = productLikeRepository.findByProductIdAndUserId(command.productId(), userId);
        if (existingLike.isEmpty()) {
            return; // Not liked, do nothing (idempotent)
        }

        // Remove like
        productLikeRepository.delete(existingLike.get());

        // Update cached like count
        product.decrementLikeCount();
        productRepository.save(product);

        // Publish domain event for analytics (Phase 3)
        var event = new ProductUnlikedEvent(command.productId(), command.userId(), Instant.now());
        eventPublisher.publishEvent(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean handle(CheckIfUserLikedProductQuery query) {
        var userId = UserId.of(query.userId());
        return productLikeRepository.existsByProductIdAndUserId(query.productId(), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long handle(GetLikeCountByProductQuery query) {
        // Return cached count from Product for better performance
        var product = productRepository.findById(query.productId());
        return product.map(Product::getLikeCount).orElse(0L);
    }

    // Domain Events for Analytics (Phase 3)
    public record ProductLikedEvent(java.util.UUID productId, java.util.UUID userId, Instant timestamp) {}
    public record ProductUnlikedEvent(java.util.UUID productId, java.util.UUID userId, Instant timestamp) {}
}
