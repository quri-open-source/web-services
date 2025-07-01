package quri.teelab.api.teelab.productcatalog.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetAllProductsQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductByIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductsByProjectIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.SearchProductsByTagsQuery;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CreateProductResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.ProductResourceFromEntityAssembler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Product Catalog")
public class ProductsController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products from the catalog, optionally filtered by project or tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    public ResponseEntity<List<ProductResource>> getAllProducts(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "tags", required = false) List<String> tags) {
        
        List<ProductResource> productResources;
        
        if (projectId != null) {
            var products = productQueryService.handle(new GetProductsByProjectIdQuery(projectId));
            if (products.isEmpty()) return ResponseEntity.notFound().build();
            productResources = products.stream()
                    .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else if (tags != null && !tags.isEmpty()) {
            var products = productQueryService.handle(new SearchProductsByTagsQuery(tags));
            if (products.isEmpty()) return ResponseEntity.notFound().build();
            productResources = products.stream()
                    .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else {
            var products = productQueryService.handle(new GetAllProductsQuery());
            if (products.isEmpty()) return ResponseEntity.notFound().build();
            productResources = products.stream()
                    .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(productResources);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Get a specific product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResource> getProductById(@PathVariable UUID productId) {
        var product = productQueryService.handle(new GetProductByIdQuery(productId));
        if (product.isEmpty()) return ResponseEntity.notFound().build();

        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return ResponseEntity.ok(productResource);
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product in the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource resource) {
        var createProductCommand = CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
        var productId = productCommandService.handle(createProductCommand);

        if (productId == null) return ResponseEntity.badRequest().build();

        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var product = productQueryService.handle(getProductByIdQuery);

        if (product.isEmpty()) return ResponseEntity.notFound().build();

        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return new ResponseEntity<>(productResource, HttpStatus.CREATED);
    }
}
