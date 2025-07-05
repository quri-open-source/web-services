package quri.teelab.api.teelab.orderprocessing.interfaces.acl;

import java.util.List;
import java.util.UUID;

public interface OrderProcessingContextFacade {
    /**
     * Fetch all orders for a user
     * @param userId The user ID
     * @return List of OrderDto
     */
    List<OrderDto> fetchOrdersByUserId(UUID userId);

    /**
     * Fetch a single order by its ID
     * @param orderId The order ID
     * @return OrderDto or null if not found
     */
    OrderDto fetchOrderById(UUID orderId);

    /**
     * Fetch all items for a specific order
     * @param orderId The order ID
     * @return List of ItemDto for the given order
     */
    List<ItemDto> fetchItemsByOrderId(UUID orderId);

    /**
     * Check if an order exists
     * @param orderId The order ID to check
     * @return true if the order exists, false otherwise
     */
    boolean orderExists(UUID orderId);
}

