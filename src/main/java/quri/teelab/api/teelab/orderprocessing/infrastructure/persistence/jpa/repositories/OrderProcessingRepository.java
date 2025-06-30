package quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * OrderProcessingRepository interface provides methods to access order processing data.
 */

public interface OrderProcessingRepository
        extends JpaRepository<OrderProcessing, OrderId> {
    List<OrderProcessing> findAllByUserId(UUID userId);
}
