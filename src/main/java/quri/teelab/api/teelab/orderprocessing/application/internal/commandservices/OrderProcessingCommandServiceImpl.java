package quri.teelab.api.teelab.orderprocessing.application.internal.commandservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingCommandService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.OrderProcessingRepository;
@Service
@Transactional
public class OrderProcessingCommandServiceImpl implements OrderProcessingCommandService {

    private final OrderProcessingRepository repository;

    public OrderProcessingCommandServiceImpl(OrderProcessingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createOrder(CreateOrderCommand command) {
        // Create and persist a new OrderProcessing aggregate
        OrderProcessing order = new OrderProcessing(command);
        repository.save(order);
    }

    @Override
    public void processOrder(ProcessOrderCommand command) {

        OrderProcessing order = repository.findById(command.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + command.getOrderId()));
        // Process the order with the provided payment method
        repository.save(order);
    }
}
