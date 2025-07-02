package quri.teelab.api.teelab.orderfulfillment.domain.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.ProductId;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "fulfillment_items",
        indexes = {@Index(name = "idx_fulfillment_items_product_id", columnList = "product_id")})
public class FulfillmentItem {
    @EmbeddedId
    private FulfillmentItemId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "product_id", nullable = false))
    private ProductId productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FulfillmentItemStatus status;

    @Column(name = "fulfillment_id", nullable = false, insertable = false, updatable = false)
    private UUID fulfillmentId;

    public FulfillmentItem(FulfillmentItemId id, ProductId productId, int quantity, FulfillmentItemStatus status, UUID fulfillmentId) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.fulfillmentId = fulfillmentId;
    }

    public void markAsShipped() {
        if (this.status != FulfillmentItemStatus.PENDING) {
            throw new IllegalStateException("Only pending items can be shipped.");
        }
        this.status = FulfillmentItemStatus.SHIPPED;
    }

    public void markAsReceived() {
        if (this.status != FulfillmentItemStatus.SHIPPED) {
            throw new IllegalStateException("Only shipped items can be received.");
        }
        this.status = FulfillmentItemStatus.RECEIVED;
    }
}

