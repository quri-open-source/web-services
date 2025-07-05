package quri.teelab.api.teelab.analytics.application.internal.queryservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.analytics.domain.model.queries.GetManufacturerAnalyticsByManufacturerIdQuery;
import quri.teelab.api.teelab.analytics.interfaces.rest.resources.ManufacturerAnalyticsResource;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentStatus;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.FulfillmentRepository;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.ManufacturerRepository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ManufacturerAnalyticsRealTimeQueryService {
    
    private final FulfillmentRepository fulfillmentRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerAnalyticsRealTimeQueryService(FulfillmentRepository fulfillmentRepository, 
                                                    ManufacturerRepository manufacturerRepository) {
        this.fulfillmentRepository = fulfillmentRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public Optional<ManufacturerAnalyticsResource> handle(GetManufacturerAnalyticsByManufacturerIdQuery query) {
        // First verify the manufacturer exists
        var manufacturer = manufacturerRepository.findById(query.getManufacturerId());
        if (manufacturer.isEmpty()) {
            return Optional.empty();
        }

        // Get all fulfillments for this manufacturer
        var fulfillments = fulfillmentRepository.findByManufacturer_Id(query.getManufacturerId());
        
        // Calculate KPIs
        int totalOrdersReceived = fulfillments.size();
        
        int pendingFulfillments = (int) fulfillments.stream()
                .filter(f -> f.getStatus() == FulfillmentStatus.PENDING || f.getStatus() == FulfillmentStatus.PROCESSING)
                .count();
        
        int producedProjects = (int) fulfillments.stream()
                .filter(f -> f.getStatus() == FulfillmentStatus.SHIPPED || f.getStatus() == FulfillmentStatus.DELIVERED)
                .count();
        
        // Calculate average fulfillment time in days
        double avgFulfillmentTimeDays = fulfillments.stream()
                .filter(f -> f.getReceivedDate() != null && f.getShippedDate() != null)
                .mapToLong(f -> {
                    long diffInMillis = f.getShippedDate().getTime() - f.getReceivedDate().getTime();
                    return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
                })
                .average()
                .orElse(0.0);

        return Optional.of(new ManufacturerAnalyticsResource(
                manufacturer.get().getUserId().toString(),
                totalOrdersReceived,
                pendingFulfillments,
                producedProjects,
                avgFulfillmentTimeDays
        ));
    }
}
