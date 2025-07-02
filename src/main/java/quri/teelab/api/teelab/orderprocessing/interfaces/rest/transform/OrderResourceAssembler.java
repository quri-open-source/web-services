package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ItemResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.OrderResource;

import java.util.stream.Collectors;


public class OrderResourceAssembler {

    public static OrderResource toResource(OrderProcessing order) {

        return new OrderResource(
                order.getId(),
                order.getUserId(),
                order.getItems().stream()
                        .map(i -> new ItemResource(
                                i.getId(),
                                i.getProjectId(),
                                i.getQuantity()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
