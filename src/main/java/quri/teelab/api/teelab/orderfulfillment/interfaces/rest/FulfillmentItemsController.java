package quri.teelab.api.teelab.orderfulfillment.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsShippedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsReceivedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CancelFulfillmentItemCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllFulfillmentItemsByFulfillmentIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemCommandService;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemQueryService;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.FulfillmentItemResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.UpdateFulfillmentItemStatusResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.CreateUpdateFulfillmentItemStatusCommandFromResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.FulfillmentItemResourceFromEntityAssembler;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/fulfillment-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fulfillment Items", description = "Available Fulfillment Item Endpoints")
@RequiredArgsConstructor
public class FulfillmentItemsController {

    private final FulfillmentItemCommandService fulfillmentItemCommandService;
    private final FulfillmentItemQueryService fulfillmentItemQueryService;

    @GetMapping("/{fulfillmentId}")
    @Operation(summary = "Get all fulfillment items by fulfillment ID", description = "Retrieve all fulfillment items associated with a specific fulfillment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment items retrieved successfully"), 
        @ApiResponse(responseCode = "404", description = "No fulfillment items found for the given fulfillment ID")
    })
    public ResponseEntity<List<FulfillmentItemResource>> getAllFulfillmentItemsByFulfillmentId(@PathVariable UUID fulfillmentId) {
        var getAllFulfillmentItemsByFulfillmentIdQuery = new GetAllFulfillmentItemsByFulfillmentIdQuery(fulfillmentId);
        var fulfillmentItems = fulfillmentItemQueryService.handle(getAllFulfillmentItemsByFulfillmentIdQuery);
        
        if (fulfillmentItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var fulfillmentItemResources = fulfillmentItems.stream()
            .map(FulfillmentItemResourceFromEntityAssembler::toResourceFromEntity)
            .toList();
        return ResponseEntity.ok(fulfillmentItemResources);
    }

    @PatchMapping("/{fulfillmentItemId}")
    @Operation(summary = "Mark fulfillment item as shipped", description = "Mark a fulfillment item as shipped by changing its status from PENDING to SHIPPED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment item marked as shipped successfully"), 
        @ApiResponse(responseCode = "400", description = "Invalid fulfillment item ID or item cannot be shipped"),
        @ApiResponse(responseCode = "404", description = "Fulfillment item not found")
    })
    public ResponseEntity<FulfillmentItemResource> updateFulfillmentItemStatus(@RequestBody UpdateFulfillmentItemStatusResource resource, @PathVariable String fulfillmentItemId) {
        var updateFulfillmentItemStatusCommand = CreateUpdateFulfillmentItemStatusCommandFromResource.toCommandFromResource(resource, fulfillmentItemId);

        try {
            var fulfillmentItem = fulfillmentItemCommandService.handle(updateFulfillmentItemStatusCommand);
            
            if (fulfillmentItem.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            var fulfillmentItemResource = FulfillmentItemResourceFromEntityAssembler.toResourceFromEntity(fulfillmentItem.get());
            
            return ResponseEntity.ok(fulfillmentItemResource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}