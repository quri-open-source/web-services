package quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Fulfillment;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.OrderId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FulfillmentRepository extends JpaRepository<Fulfillment, UUID> {
    List<Fulfillment> findByManufacturer_Id(UUID manufacturerId);

    Optional<Fulfillment> findByOrderId_Value(UUID orderId);

    /**
     * Check if a fulfillment already exists for the given orderId and manufacturerId
     * to prevent duplicate fulfillments.
     * 
     * @param orderId The order ID value object
     * @param manufacturerId The manufacturer ID
     * @return true if a fulfillment exists, false otherwise
     */
    boolean existsByOrderIdAndManufacturer_Id(OrderId orderId, UUID manufacturerId);
}
