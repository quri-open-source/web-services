package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Address;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.AddressResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.DiscountResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ItemResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.OrderResource;
import java.util.stream.Collectors;

public class OrderResourceAssembler {

    public static OrderResource toResource(OrderProcessing order) {
        return new OrderResource(
                order.orderId().orderId(),
                order.getUserId(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getTotalAmount().amount(),
                order.getTransactionId(),
                order.getDescription(),
                toAddressResource(order.getShippingAddress()),
                order.getAppliedDiscounts().stream()
                        .map(d -> new DiscountResource(d.getId()))
                        .collect(Collectors.toList()),
                order.getItems().stream()
                        .map(i -> new ItemResource(
                                i.getId(),
                                i.getProjectId(),
                                i.getQuantity(),
                                i.getUnitPrice()
                        ))
                        .collect(Collectors.toList())
        );
    }

    private static AddressResource toAddressResource(Address addr) {
        return new AddressResource(
                addr.address(),
                addr.city(),
                addr.state(),
                addr.zip(),
                addr.country()
        );
    }
}
