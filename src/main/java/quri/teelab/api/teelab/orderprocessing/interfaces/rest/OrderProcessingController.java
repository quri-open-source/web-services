package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.OrderProcessing;
import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrderByIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.GetOrdersByUserIdQuery;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingCommandService;
import quri.teelab.api.teelab.orderprocessing.domain.services.OrderProcessingQueryService;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.CreateOrderResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.OrderResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform.OrderResourceAssembler;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Available Order Processing Endpoints")
public class OrderProcessingController {

    private final OrderProcessingCommandService commandService;
    private final OrderProcessingQueryService queryService;

    public OrderProcessingController(OrderProcessingCommandService commandService,
                                     OrderProcessingQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get orders", description = "Get orders by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders found"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID"),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    public ResponseEntity<?> getOrdersByUser(@RequestParam("userId") UUID userId) {
        var orders = queryService.getOrdersByUser(new GetOrdersByUserIdQuery(userId)).stream()
                .map(OrderResourceAssembler::toResource)
                .toList();
        
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("This user does not have any orders.");
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Get a specific order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<?> getOrderById(@PathVariable UUID orderId) {
        try {
            OrderResource resource = OrderResourceAssembler.toResource(
                    queryService.getOrderById(new GetOrderByIdQuery(orderId))
            );
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No order found with the provided ID.");
        }
    }

    @PostMapping
    @Operation(summary = "Create order", description = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderResource resource) {
        try {
            CreateOrderCommand cmd = CreateOrderCommandFromResourceAssembler.toCommand(resource);
            OrderProcessing createdOrder = commandService.createOrder(cmd);
            OrderResource orderResource = OrderResourceAssembler.toResource(createdOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderResource);
        } catch (Exception e) {
            // Return error message for debugging
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

}
