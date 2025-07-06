package quri.teelab.api.teelab.orderprocessing.application.acl;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrderByIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrdersByUserIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingQueryService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.OrderProcessingRepository;
import quri.teelab.api.teelab.orderprocessing.interfaces.acl.ItemDto;
import quri.teelab.api.teelab.orderprocessing.interfaces.acl.OrderDto;
import quri.teelab.api.teelab.orderprocessing.interfaces.acl.OrderProcessingContextFacade;

import java.util.List;
import java.util.UUID;

@Service
public class OrderProcessingContextFacadeImpl implements OrderProcessingContextFacade {
    private final OrderProcessingQueryService queryService;
    private final OrderProcessingRepository orderProcessingRepository;

    public OrderProcessingContextFacadeImpl(OrderProcessingQueryService queryService, OrderProcessingRepository orderProcessingRepository) {
        this.queryService = queryService;
        this.orderProcessingRepository = orderProcessingRepository;
    }

    @Override
    public List<OrderDto> fetchOrdersByUserId(UUID userId) {
        return queryService.getOrdersByUser(new GetOrdersByUserIdQuery(userId))
                .stream()
                .flatMap(order -> order.getItems().stream().map(item -> new OrderDto(
                        order.getId(),
                        order.getUserId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getId()
                )))
                .toList();
    }

    @Override
    public OrderDto fetchOrderById(UUID orderId) {
        var order = queryService.getOrderById(new GetOrderByIdQuery(orderId));
        if (order == null || order.getItems().isEmpty()) return null;
        var item = order.getItems().get(0);
        return new OrderDto(
                order.getId(),
                order.getUserId(),
                item.getProductId(),
                item.getQuantity(),
                item.getId()
        );
    }

    @Override
    public List<ItemDto> fetchItemsByOrderId(UUID orderId) {
        var order = queryService.getOrderById(new GetOrderByIdQuery(orderId));
        if (order == null) return List.of();
        
        return order.getItems().stream()
                .map(item -> new ItemDto(
                        item.getId(),
                        order.getId(),
                        item.getProductId(),
                        item.getQuantity()
                ))
                .toList();
    }

    @Override
    public boolean orderExists(UUID orderId) {
        var order = queryService.getOrderById(new GetOrderByIdQuery(orderId));
        return order != null;
    }

    @Override
    public boolean completeOrder(UUID orderId) {
        var order = queryService.getOrderById(new GetOrderByIdQuery(orderId));
        if (order == null) {
            return false; // Order does not exist
        }
        var completed = order.complete();
        orderProcessingRepository.save(order);
        return completed;
    }
}
