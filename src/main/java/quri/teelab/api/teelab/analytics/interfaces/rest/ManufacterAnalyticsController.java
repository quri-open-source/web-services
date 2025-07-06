package quri.teelab.api.teelab.analytics.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quri.teelab.api.teelab.analytics.application.internal.queryservices.ManufacturerAnalyticsRealTimeQueryServiceImpl;
import quri.teelab.api.teelab.analytics.domain.model.queries.GetManufacturerAnalyticsByManufacturerIdQuery;
import quri.teelab.api.teelab.analytics.interfaces.rest.resources.ManufacturerAnalyticsResource;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/analytics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Analytics", description = "Analytics endpoints for customers and manufacturers")
public class ManufacterAnalyticsController {
    private final ManufacturerAnalyticsRealTimeQueryServiceImpl manufacturerAnalyticsQueryService;

    public ManufacterAnalyticsController(ManufacturerAnalyticsRealTimeQueryServiceImpl manufacturerAnalyticsQueryService) {
        this.manufacturerAnalyticsQueryService = manufacturerAnalyticsQueryService;
    }

    /**
     * Get analytics metrics for a manufacturer by manufacturerId.
     *
     * @param manufacturerId the manufacturer identifier
     * @return Manufacturer analytics metrics
     */
    @GetMapping("/manufacturer-kpis/{manufacturerId}")
    @Operation(
            summary = "Get manufacturer analytics KPIs",
            description = "Returns analytics KPIs related to production and fulfillment for a manufacturer, such as total orders received, pending fulfillments, produced projects, and average fulfillment time."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manufacturer analytics found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Manufacturer analytics not found for the given manufacturerId"),
            @ApiResponse(responseCode = "400", description = "Invalid manufacturer ID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ManufacturerAnalyticsResource> getManufacturerAnalytics(@PathVariable String manufacturerId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(manufacturerId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        
        var query = new GetManufacturerAnalyticsByManufacturerIdQuery(uuid);
        var analytics = manufacturerAnalyticsQueryService.handle(query);
        
        if (analytics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(analytics.get());
    }
}
