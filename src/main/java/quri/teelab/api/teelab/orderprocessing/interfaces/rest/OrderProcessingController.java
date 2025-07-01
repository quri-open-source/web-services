package quri.teelab.api.teelab.orderprocessing.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<OrderResource>> getOrdersByUser(@RequestParam("userId") UUID userId) {
        var orders = queryService.getOrdersByUser(userId).stream()
                .map(OrderResourceAssembler::toResource)
                .toList();
        
        if (orders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Get a specific order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResource> getOrderById(@PathVariable UUID orderId) {
        try {
            OrderResource resource = OrderResourceAssembler.toResource(
                    queryService.getOrderById(quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects.OrderId.of(orderId))
            );
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create order", description = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderResource resource) {
        try {
            CreateOrderCommand cmd = CreateOrderCommandFromResourceAssembler.toCommand(resource);
            commandService.createOrder(cmd);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Process order", description = "Process an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Void> processOrder(@PathVariable UUID orderId,
                                             @Valid @RequestBody ProcessOrderResource resource) {
        try {
            ProcessOrderCommand cmd = ProcessOrderCommandFromResourceAssembler.toCommand(resource);
            commandService.processOrder(cmd);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
