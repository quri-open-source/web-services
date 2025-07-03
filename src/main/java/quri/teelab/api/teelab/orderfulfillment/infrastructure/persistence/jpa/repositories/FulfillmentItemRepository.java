package quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;

import java.util.List;
import java.util.UUID;

@Repository
public interface FulfillmentItemRepository extends JpaRepository<FulfillmentItem, FulfillmentItemId> {
    List<FulfillmentItem> findByFulfillmentId(UUID fulfillmentId);
}
