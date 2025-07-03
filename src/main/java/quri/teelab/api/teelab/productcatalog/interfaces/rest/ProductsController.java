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
import quri.teelab.api.teelab.productcatalog.domain.model.commands.DeleteProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductStatusCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CreateProductResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.UpdateProductResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import quri.teelab.api.teelab.designlab.interfaces.acl.ProjectContextFacade;

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
    private final ProjectContextFacade projectContextFacade;

    public ProductsController(ProductCommandService productCommandService, 
                             ProductQueryService productQueryService,
                             ProjectContextFacade projectContextFacade) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
        this.projectContextFacade = projectContextFacade;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products from the catalog, optionally filtered by project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductResource>> getAllProducts(
            @RequestParam(value = "projectId", required = false) String projectId) {
        
        List<ProductResource> productResources;
        
        if (projectId != null) {
            var products = productQueryService.handle(new GetProductsByProjectIdQuery(projectId));
            productResources = products.stream()
                    .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                    .collect(Collectors.toList());
        } else {
            var products = productQueryService.handle(new GetAllProductsQuery());
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
    @Operation(summary = "Create product", description = "Create a new product in the catalog based on a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource resource) {
        try {
            // Validate the resource
            if (resource == null) {
                return ResponseEntity.badRequest().build();
            }

            // Create the product command using the project context facade
            var createProductCommand = CreateProductCommandFromResourceAssembler.toCommandFromResource(
                    resource, projectContextFacade);
            var productId = productCommandService.handle(createProductCommand);

            if (productId == null) {
                return ResponseEntity.badRequest().build();
            }

            // Retrieve the created product
            var getProductByIdQuery = new GetProductByIdQuery(productId);
            var product = productQueryService.handle(getProductByIdQuery);

            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
            return new ResponseEntity<>(productResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Update product", description = "Update specific fields of an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResource> updateProduct(@PathVariable UUID productId, 
                                          @RequestBody UpdateProductResource resource) {
        try {
            // Validate the resource
            if (resource == null) {
                return ResponseEntity.badRequest().build();
            }

            // Update status if provided
            if (resource.status() != null && !resource.status().trim().isEmpty()) {
                ProductStatus productStatus;
                try {
                    productStatus = ProductStatus.valueOf(resource.status().toUpperCase());
                    var updateStatusCommand = new UpdateProductStatusCommand(productId, productStatus);
                    productCommandService.handle(updateStatusCommand);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().build();
                }
            }

            // Update price if provided
            if (resource.priceAmount() != null) {
                String currency = (resource.priceCurrency() != null && !resource.priceCurrency().trim().isEmpty()) 
                    ? resource.priceCurrency() : "PEN"; // Default currency
                
                try {
                    var money = new Money(resource.priceAmount(), java.util.Currency.getInstance(currency));
                    var updatePriceCommand = new UpdateProductPriceCommand(productId, money);
                    productCommandService.handle(updatePriceCommand);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            }

            // Retrieve the updated product
            var getProductByIdQuery = new GetProductByIdQuery(productId);
            var product = productQueryService.handle(getProductByIdQuery);

            if (product.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
            return ResponseEntity.ok(productResource);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Delete an existing product from the catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        try {
            // Delete the product
            var deleteProductCommand = new DeleteProductCommand(productId);
            productCommandService.handle(deleteProductCommand);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
