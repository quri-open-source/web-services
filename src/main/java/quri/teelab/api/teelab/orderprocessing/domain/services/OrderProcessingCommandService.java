package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;

public interface OrderProcessingCommandService {

    void createOrder(CreateOrderCommand command);

    void processOrder(ProcessOrderCommand command);
}
