package quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;

import java.util.List;
import java.util.UUID;

/**
 * OrderProcessingRepository interface provides methods to access order processing data.
 */

public interface OrderProcessingRepository
        extends JpaRepository<OrderProcessing, UUID> {
    List<OrderProcessing> findAllByUserId(UUID userId);
}
