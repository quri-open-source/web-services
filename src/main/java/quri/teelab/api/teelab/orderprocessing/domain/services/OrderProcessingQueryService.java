package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId;
import java.util.List;
import java.util.UUID;

public interface OrderProcessingQueryService {

    List<OrderProcessing> getOrdersByUser(UUID userId);

    OrderProcessing getOrderById(OrderId orderId);
}