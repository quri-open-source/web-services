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
import quri.teelab.api.teelab.analytics.application.internal.queryservices.CustomerAnalyticsQueryServiceImpl;
import quri.teelab.api.teelab.analytics.domain.model.queries.GetCustomerAnalyticsByUserIdQuery;
import quri.teelab.api.teelab.analytics.interfaces.rest.resources.CustomerAnalyticsResource;
import quri.teelab.api.teelab.analytics.interfaces.rest.transform.CustomerAnalyticsResourceFromEntityAssembler;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/analytics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Analytics", description = "Analytics endpoints for customers and manufacturers")
public class CustomerAnalyticsController {
    private final CustomerAnalyticsQueryServiceImpl customerAnalyticsQueryService;

    public CustomerAnalyticsController(CustomerAnalyticsQueryServiceImpl customerAnalyticsQueryService) {
        this.customerAnalyticsQueryService = customerAnalyticsQueryService;
    }

    /**
     * Get analytics metrics for a customer by userId.
     *
     * @param userId the customer user identifier
     * @return Customer analytics metrics
     */
    @GetMapping("/customer/{userId}")
    @Operation(
            summary = "Get customer analytics",
            description = "Returns analytics metrics related to design activities for a customer, such as total projects, blueprints, designed garments, and completed projects."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer analytics found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Customer analytics not found for the given userId"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CustomerAnalyticsResource> getCustomerAnalytics(@PathVariable String userId) {
        UUID uuid;
        try {
            uuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        var query = new GetCustomerAnalyticsByUserIdQuery(uuid);
        var analytics = customerAnalyticsQueryService.handle(query);
        if (analytics == null) {
            return ResponseEntity.notFound().build();
        }
        var response = CustomerAnalyticsResourceFromEntityAssembler.toResponse(analytics);
        return ResponseEntity.ok(response);
    }
}
