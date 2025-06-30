package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingCommandService;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingQueryService;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.CreateOrderResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.OrderResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ProcessOrderResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.OrderResourceAssembler;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.ProcessOrderCommandFromResourceAssembler;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderProcessingController {

    private final OrderProcessingCommandService commandService;
    private final OrderProcessingQueryService queryService;

    public OrderProcessingController(OrderProcessingCommandService commandService,
                                     OrderProcessingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderResource resource) {
        CreateOrderCommand cmd = CreateOrderCommandFromResourceAssembler.toCommand(resource);
        commandService.createOrder(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<OrderResource> getOrdersByUser(@RequestParam("user_id") UUID userId) {
        return queryService.getOrdersByUser(userId).stream()
                .map(OrderResourceAssembler::toResource)
                .toList();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResource> getOrderById(@PathVariable UUID orderId) {
        OrderResource resource = OrderResourceAssembler.toResource(
                queryService.getOrderById(quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId.of(orderId))
        );
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/{orderId}/process")
    public ResponseEntity<Void> processOrder(@PathVariable UUID orderId,
                                             @Valid @RequestBody ProcessOrderResource resource) {
        ProcessOrderCommand cmd = ProcessOrderCommandFromResourceAssembler.toCommand(resource);
        commandService.processOrder(cmd);
        return ResponseEntity.ok().build();
    }
}
