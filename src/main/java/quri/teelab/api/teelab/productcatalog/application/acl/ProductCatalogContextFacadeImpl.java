package quri.teelab.api.teelab.productcatalog.application.acl;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductByIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductsByProjectIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.interfaces.acl.ProductCatalogContextFacade;
import quri.teelab.api.teelab.productcatalog.interfaces.acl.ProductCatalogContextFacade.ProductInfo;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.designlab.interfaces.acl.ProjectContextFacade;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductCatalogContextFacade that provides a unified interface
 * for other bounded contexts to interact with the Product Catalog context.
 */
@Service
public class ProductCatalogContextFacadeImpl implements ProductCatalogContextFacade {
    private final static String DEFAULT_CURRENCY = "PEN"; // Default currency for products

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;
    private final ProjectContextFacade projectContextFacade;

    public ProductCatalogContextFacadeImpl(ProductCommandService productCommandService, 
                                          ProductQueryService productQueryService,
                                          ProjectContextFacade projectContextFacade) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
        this.projectContextFacade = projectContextFacade;
    }

    @Override
    public UUID createProduct(String projectId, BigDecimal price, String currency, String status) {
        // Validate inputs
        if (projectId == null || projectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Project ID cannot be null or empty");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        
        UUID projectUuid;
        try {
            projectUuid = UUID.fromString(projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid project ID format: " + projectId);
        }
        
        // Check if project exists first
        if (!projectContextFacade.projectExists(projectUuid)) {
            throw new IllegalArgumentException("Project does not exist with ID: " + projectId);
        }
        
        // Get project details from DesignLab
        var projectDetails = projectContextFacade.fetchProjectDetailsForProduct(projectUuid);
        
        if (projectDetails == null) {
            throw new IllegalArgumentException("Project details not found for ID: " + projectId);
        }
        
        // Parse status or default to AVAILABLE
        ProductStatus productStatus = ProductStatus.AVAILABLE;
        if (status != null && !status.trim().isEmpty()) {
            try {
                productStatus = ProductStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + status + 
                    ". Valid statuses are: AVAILABLE, UNAVAILABLE, OUT_OF_STOCK, DISCONTINUED");
            }
        }
        
        // Use provided currency or default
        String currencyToUse = (currency != null && !currency.trim().isEmpty()) ? currency : DEFAULT_CURRENCY;
        
        var createProductCommand = new CreateProductCommand(
                projectId,
                new Money(price, Currency.getInstance(currencyToUse)),
                productStatus,
                projectDetails.title(),
                projectDetails.previewUrl(),
                projectDetails.userId(),
                projectDetails.size(),
                projectDetails.gender(),
                projectDetails.color()
        );

        return productCommandService.handle(createProductCommand);
    }

    @Override
    public boolean productExists(UUID productId) {
        var query = new GetProductByIdQuery(productId);
        var productOptional = productQueryService.handle(query);
        return productOptional.isPresent();
    }

    @Override
    public Optional<ProductInfo> getProductInfo(UUID productId) {
        var query = new GetProductByIdQuery(productId);
        var productOptional = productQueryService.handle(query);

        return productOptional.map(this::mapToProductInfo);
    }

    @Override
    public List<ProductInfo> getProductsByProject(String projectId) {
        // Validate input
        if (projectId == null || projectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Project ID cannot be null or empty");
        }
        
        // Validate project ID format
        try {
            UUID.fromString(projectId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid project ID format: " + projectId);
        }
        
        var query = new GetProductsByProjectIdQuery(projectId);
        var products = productQueryService.handle(query);

        return products.stream().map(this::mapToProductInfo).collect(Collectors.toList());
    }

    @Override
    public boolean updateProductPrice(UUID productId, BigDecimal price, String currency) {
        try {
            // Use the provided currency, or default if not provided
            String currencyToUse = (currency != null && !currency.trim().isEmpty()) ? currency : DEFAULT_CURRENCY;
            var command = new UpdateProductPriceCommand(productId, new Money(price, Currency.getInstance(currencyToUse)));
            productCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean projectHasProducts(String projectId) {
        if (projectId == null || projectId.trim().isEmpty()) {
            return false;
        }
        
        try {
            UUID.fromString(projectId);
        } catch (IllegalArgumentException e) {
            return false;
        }
        
        var query = new GetProductsByProjectIdQuery(projectId);
        var products = productQueryService.handle(query);
        return !products.isEmpty();
    }

    /**
     * Maps a domain Product to the ProductInfo DTO for external contexts
     */
    private ProductInfo mapToProductInfo(Product product) {
        return new ProductInfo(
                product.getId(), 
                UUID.fromString(product.getProjectId().value()), // Convert String to UUID
                product.getPrice(), 
                product.getStatus(),
                product.getProjectTitle(),
                product.getProjectPreviewUrl(),
                product.getProjectUserId(),
                product.getLikeCount()
        );
    }
}
