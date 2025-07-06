package quri.teelab.api.teelab.orderprocessing.domain.services;

import com.stripe.model.PaymentIntent;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderIntentCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;

public interface OrderProcessingCommandService {

    OrderProcessing handle(CreateOrderCommand command);

    void handle(ProcessOrderCommand command);

    PaymentIntent handle(CreateOrderIntentCommand command);

}
