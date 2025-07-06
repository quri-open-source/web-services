package quri.teelab.api.teelab.analytics.application.internal.queryservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.analytics.domain.model.queries.GetCustomerAnalyticsByUserIdQuery;
import quri.teelab.api.teelab.analytics.interfaces.rest.resources.CustomerAnalyticsResource;
import quri.teelab.api.teelab.orderprocessing.interfaces.acl.OrderProcessingContextFacade;
import quri.teelab.api.teelab.productcatalog.interfaces.acl.ProductCatalogContextFacade;
import quri.teelab.api.teelab.designlab.interfaces.acl.ProjectContextFacade;

import java.util.Optional;

@Service
public class CustomerAnalyticsRealTimeQueryServiceImpl {
    
    private final OrderProcessingContextFacade orderProcessingFacade;
    private final ProductCatalogContextFacade productCatalogFacade;
    private final ProjectContextFacade projectFacade;
    
    public CustomerAnalyticsRealTimeQueryServiceImpl(
            OrderProcessingContextFacade orderProcessingFacade,
            ProductCatalogContextFacade productCatalogFacade,
            ProjectContextFacade projectFacade) {
        this.orderProcessingFacade = orderProcessingFacade;
        this.productCatalogFacade = productCatalogFacade;
        this.projectFacade = projectFacade;
    }
    
    public Optional<CustomerAnalyticsResource> handle(GetCustomerAnalyticsByUserIdQuery query) {
        try {
            // Get count of projects for this user (totalProjects)
            long totalProjects = projectFacade.getProjectCountByUserId(query.getUserId());
            
            // Get project IDs for this user
            var userProjectIds = projectFacade.fetchProjectIdsByUserId(query.getUserId());
            
            // Blueprints are projects that exist but don't have products yet
            int blueprints = 0;
            int designedGarments = 0;
            
            for (var projectId : userProjectIds) {
                boolean hasProducts = productCatalogFacade.projectHasProducts(projectId.toString());
                if (hasProducts) {
                    designedGarments++;
                } else {
                    blueprints++;
                }
            }
            
            // Get completed orders (orders that have been processed)
            var userOrders = orderProcessingFacade.fetchOrdersByUserId(query.getUserId());
            int completed = userOrders.size(); // Assuming all orders in the system are completed
            
            return Optional.of(new CustomerAnalyticsResource(
                    query.getUserId().toString(),
                    (int) totalProjects,
                    blueprints,
                    designedGarments,
                    completed,
                    query.getUserId().toString() // Using userId as ID for simplicity
            ));
            
        } catch (Exception e) {
            // Return empty analytics if there's any error
            return Optional.of(new CustomerAnalyticsResource(
                    query.getUserId().toString(),
                    0,
                    0,
                    0,
                    0,
                    query.getUserId().toString()
            ));
        }
    }
}
