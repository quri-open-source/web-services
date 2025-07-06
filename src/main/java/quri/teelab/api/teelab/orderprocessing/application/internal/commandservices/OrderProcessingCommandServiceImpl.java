package quri.teelab.api.teelab.orderprocessing.application.internal.commandservices;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderIntentCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingCommandService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.OrderProcessingRepository;
import quri.teelab.api.teelab.shared.infrastructure.stripe.StripeService;

@Service
@Transactional
public class OrderProcessingCommandServiceImpl implements OrderProcessingCommandService {

    private final OrderProcessingRepository repository;
    private final StripeService stripeService;

    public OrderProcessingCommandServiceImpl(OrderProcessingRepository repository, StripeService stripeService) {
        this.repository = repository;
        this.stripeService = stripeService;
    }

    @Override
    public OrderProcessing handle(CreateOrderCommand command) {
        // Create and persist a new OrderProcessing aggregate
        OrderProcessing order = new OrderProcessing(command);
        return repository.save(order);
    }

    @Override
    public void handle(ProcessOrderCommand command) {

        OrderProcessing order = repository.findById(command.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + command.getOrderId()));
        // Process the order with the provided payment method
        repository.save(order);
    }

    @Override
    public PaymentIntent handle(CreateOrderIntentCommand command) {
        try {
            return stripeService.createPaymentIntent(command.amount());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
