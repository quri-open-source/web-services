package quri.teelab.api.teelab.orderprocessing.application.internal.queryservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrderByIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrdersByUserIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingQueryService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.OrderProcessingRepository;

import java.util.List;

@Service
public class OrderProcessingQueryServiceImpl implements OrderProcessingQueryService {

    private final OrderProcessingRepository repository;

    public OrderProcessingQueryServiceImpl(OrderProcessingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrderProcessing> getOrdersByUser(GetOrdersByUserIdQuery query) {
        return repository.findAllByUserId(query.userId());
    }

    @Override
    public OrderProcessing getOrderById(GetOrderByIdQuery query) {
        return repository.findById(query.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + query.orderId()));
    }
}
