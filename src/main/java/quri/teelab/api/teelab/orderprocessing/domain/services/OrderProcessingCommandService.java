package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;

public interface OrderProcessingCommandService {

    OrderProcessing createOrder(CreateOrderCommand command);

    void processOrder(ProcessOrderCommand command);
}
