package quri.teelab.api.teelab.orderprocessing.application.internal.queryservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingQueryService;
import quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.OrderProcessingRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderProcessingQueryServiceImpl implements OrderProcessingQueryService {

    private final OrderProcessingRepository repository;

    public OrderProcessingQueryServiceImpl(OrderProcessingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderProcessing> getOrdersByUser(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public OrderProcessing getOrderById(OrderId orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
    }
}
