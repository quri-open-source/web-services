package quri.teelab.api.teelab.orderprocessing.domain.model.commands;

import java.util.UUID;

/**
 * ProcessOrderCommand is used to process an existing order in the order processing system.
 * It contains the order ID and the payment method to be used for processing the order.
 */

public class ProcessOrderCommand {
    private final UUID orderId;
    private final String paymentMethod;

    public ProcessOrderCommand(UUID orderId, String paymentMethod) {
        this.orderId       = orderId;
        this.paymentMethod = paymentMethod;
    }

    public UUID getOrderId()        { return orderId; }
    public String getPaymentMethod(){ return paymentMethod; }
}
