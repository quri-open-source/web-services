package quri.teelab.api.teelab.orderfulfillment.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllFulfillmentsByManufacturerIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetFulfillmentByIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentCommandService;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentQueryService;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.CreateFulfillmentResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.FulfillmentResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.CreateFulfillmentCommandFromResourceAssembler;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.FulfillmentResourceFromEntityAssembler;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/fulfillments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fulfillments", description = "Available Fulfillment Endpoints")
public class FulfillmentsController {

    private final FulfillmentCommandService fulfillmentCommandService;
    private final FulfillmentQueryService fulfillmentQueryService;

    public FulfillmentsController(FulfillmentCommandService fulfillmentCommandService, FulfillmentQueryService fulfillmentQueryService) {
        this.fulfillmentCommandService = fulfillmentCommandService;
        this.fulfillmentQueryService = fulfillmentQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all fulfillments", description = "Get all fulfillments, optionally filtered by manufacturer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillments found"), 
        @ApiResponse(responseCode = "404", description = "No fulfillments found")
    })
    public ResponseEntity<List<FulfillmentResource>> getAllFulfillments(
            @RequestParam(value = "manufacturerId", required = false) String manufacturerId) {
        
        if (manufacturerId != null) {
            var getAllFulfillmentsByManufacturerIdQuery = new GetAllFulfillmentsByManufacturerIdQuery(manufacturerId);
            var fulfillments = fulfillmentQueryService.handle(getAllFulfillmentsByManufacturerIdQuery);
            if (fulfillments.isEmpty()) return ResponseEntity.notFound().build();
            var fulfillmentResources = fulfillments.stream()
                .map(FulfillmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
            return ResponseEntity.ok(fulfillmentResources);
        }
        
        // TODO: Implement GetAllFulfillmentsQuery for when no manufacturerId is provided
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{fulfillmentId}")
    @Operation(summary = "Get fulfillment by ID", description = "Get a specific fulfillment by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment found"), 
        @ApiResponse(responseCode = "404", description = "Fulfillment not found")
    })
    public ResponseEntity<FulfillmentResource> getFulfillmentById(@PathVariable UUID fulfillmentId) {
        var getFulfillmentByIdQuery = new GetFulfillmentByIdQuery(fulfillmentId);
        var fulfillment = fulfillmentQueryService.handle(getFulfillmentByIdQuery);
        
        if (fulfillment.isEmpty()) return ResponseEntity.notFound().build();
        
        var fulfillmentResource = FulfillmentResourceFromEntityAssembler.toResourceFromEntity(fulfillment.get());
        return ResponseEntity.ok(fulfillmentResource);
    }

    @PostMapping
    @Operation(summary = "Create fulfillment", description = "Create a new fulfillment with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Fulfillment created successfully"), 
        @ApiResponse(responseCode = "400", description = "Invalid input data"), 
        @ApiResponse(responseCode = "404", description = "Fulfillment not found")
    })
    public ResponseEntity<FulfillmentResource> createFulfillment(@RequestBody CreateFulfillmentResource resource) {
        var createFulfillmentCommand = CreateFulfillmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var fulfillmentId = fulfillmentCommandService.handle(createFulfillmentCommand);
        if (fulfillmentId == null) return ResponseEntity.badRequest().build();

        var getFulfillmentByIdQuery = new GetFulfillmentByIdQuery(fulfillmentId);
        var fulfillment = fulfillmentQueryService.handle(getFulfillmentByIdQuery);
        if (fulfillment.isEmpty()) return ResponseEntity.notFound().build();

        var fulfillmentEntity = fulfillment.get();
        var fulfillmentResource = FulfillmentResourceFromEntityAssembler.toResourceFromEntity(fulfillmentEntity);
        return new ResponseEntity<>(fulfillmentResource, HttpStatus.CREATED);
    }
}
