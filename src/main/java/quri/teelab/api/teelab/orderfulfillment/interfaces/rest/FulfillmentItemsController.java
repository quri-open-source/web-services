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
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemId;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemCommandService;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.FulfillmentItemResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.FulfillmentItemResourceFromEntityAssembler;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/fulfillment-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fulfillment Items", description = "Available Fulfillment Item Endpoints")
@RequiredArgsConstructor
public class FulfillmentItemsController {

    private final FulfillmentItemCommandService fulfillmentItemCommandService;

    @PostMapping("/{fulfillmentItemId}/ship")
    @Operation(summary = "Mark fulfillment item as shipped", description = "Mark a fulfillment item as shipped by changing its status from PENDING to SHIPPED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment item marked as shipped successfully"), 
        @ApiResponse(responseCode = "400", description = "Invalid fulfillment item ID or item cannot be shipped"),
        @ApiResponse(responseCode = "404", description = "Fulfillment item not found")
    })
    public ResponseEntity<FulfillmentItemResource> markFulfillmentItemAsShipped(@PathVariable UUID fulfillmentItemId) {
        var fulfillmentItemIdVO = new FulfillmentItemId(fulfillmentItemId);
        var markFulfillmentItemAsShippedCommand = new MarkFulfillmentItemAsShippedCommand(fulfillmentItemIdVO);
        
        try {
            var fulfillmentItem = fulfillmentItemCommandService.handle(markFulfillmentItemAsShippedCommand);
            
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

    @PostMapping("/{fulfillmentItemId}/receive")
    @Operation(summary = "Mark fulfillment item as received", description = "Mark a fulfillment item as received by changing its status from SHIPPED to RECEIVED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment item marked as received successfully"), 
        @ApiResponse(responseCode = "400", description = "Invalid fulfillment item ID or item cannot be received"),
        @ApiResponse(responseCode = "404", description = "Fulfillment item not found")
    })
    public ResponseEntity<FulfillmentItemResource> markFulfillmentItemAsReceived(@PathVariable UUID fulfillmentItemId) {
        var fulfillmentItemIdVO = new FulfillmentItemId(fulfillmentItemId);
        var markFulfillmentItemAsReceivedCommand = new MarkFulfillmentItemAsReceivedCommand(fulfillmentItemIdVO);
        
        try {
            var fulfillmentItem = fulfillmentItemCommandService.handle(markFulfillmentItemAsReceivedCommand);
            
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

    @PostMapping("/{fulfillmentItemId}/cancel")
    @Operation(summary = "Cancel fulfillment item", description = "Cancel a fulfillment item by changing its status from PENDING to CANCELLED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Fulfillment item cancelled successfully"), 
        @ApiResponse(responseCode = "400", description = "Invalid fulfillment item ID or item cannot be cancelled"),
        @ApiResponse(responseCode = "404", description = "Fulfillment item not found")
    })
    public ResponseEntity<FulfillmentItemResource> cancelFulfillmentItem(@PathVariable UUID fulfillmentItemId) {
        var fulfillmentItemIdVO = new FulfillmentItemId(fulfillmentItemId);
        var cancelFulfillmentItemCommand = new CancelFulfillmentItemCommand(fulfillmentItemIdVO);
        
        try {
            var fulfillmentItem = fulfillmentItemCommandService.handle(cancelFulfillmentItemCommand);
            
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