package quri.teelab.api.teelab.productcatalog.application.acl;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductByIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductsByProjectIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.SearchProductsByTagsQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.interfaces.acl.ProductCatalogContextFacade;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

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

    public ProductCatalogContextFacadeImpl(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @Override
    public UUID createProduct(String projectId, String manufacturerId, BigDecimal price, String currency, List<String> tags, List<String> gallery, String status) {
        var createProductCommand = new CreateProductCommand(ProjectId.of(projectId), ManufacturerId.of(manufacturerId), new Money(price, Currency.getInstance(DEFAULT_CURRENCY)), tags, gallery, status);

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
        var query = new GetProductsByProjectIdQuery(projectId);
        var products = productQueryService.handle(query);

        return products.stream().map(this::mapToProductInfo).collect(Collectors.toList());
    }

    @Override
    public boolean updateProductPrice(UUID productId, BigDecimal price, String currency) {
        try {
            var command = new UpdateProductPriceCommand(productId, new Money(price, Currency.getInstance(DEFAULT_CURRENCY)));
            productCommandService.handle(command);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<UUID> searchProductsByTags(List<String> tags) {
        var query = new SearchProductsByTagsQuery(tags);
        var products = productQueryService.handle(query);

        return products.stream().map(Product::getId).collect(Collectors.toList());
    }

    /**
     * Maps a domain Product to the ProductInfo DTO for external contexts
     */
    private ProductInfo mapToProductInfo(Product product) {
        return new ProductInfo(product.getId(), new ProjectId(product.getProjectId().value()), new ManufacturerId(product.getManufacturerId().value()), product.getPrice(), product.getPrice().currency(), product.getLikes(), product.getTags(), product.getGallery(), product.getRating(), product.getStatus());
    }
}
