package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrderByIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrdersByUserIdQuery;

import java.util.List;

public interface OrderProcessingQueryService {

    List<OrderProcessing> getOrdersByUser(GetOrdersByUserIdQuery query);

    OrderProcessing getOrderById(GetOrderByIdQuery query);
}