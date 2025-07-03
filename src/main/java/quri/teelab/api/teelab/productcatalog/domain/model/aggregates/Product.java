package quri.teelab.api.teelab.productcatalog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ManufacturerId;
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
    @AttributeOverride(name = "value", column = @Column(name = "manufacturer_id", nullable = false))
    private ManufacturerId manufacturerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency", length = 3, nullable = false))
    })
    private Money price;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "product_gallery", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> gallery = new ArrayList<>();

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "status", nullable = false)
    private String status;

    public Product() {
        // Default constructor for JPA
    }

    public Product(CreateProductCommand command) {
        this.projectId = command.projectId();
        this.manufacturerId = command.manufacturerId();
        this.price = command.price();
        this.likes = 0; // Initialize with 0 likes
        this.tags = new ArrayList<>(command.tags());
        this.gallery = new ArrayList<>(command.gallery());
        this.rating = 0.0; // Initialize with 0 rating
        this.status = command.status();
    }

    public void updatePrice(Money newPrice) {
        // Validation is now handled by the Money value object itself
        this.price = newPrice;
    }



    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    public void updateRating(Double newRating) {
        if (newRating < 0 || newRating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        this.rating = newRating;
    }

    public void addTag(String tag) {
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        this.tags.remove(tag);
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void addGalleryImage(String imageUrl) {
        this.gallery.add(imageUrl);
    }

    public void removeGalleryImage(String imageUrl) {
        this.gallery.remove(imageUrl);
    }
}
