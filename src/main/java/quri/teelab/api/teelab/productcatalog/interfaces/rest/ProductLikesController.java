package quri.teelab.api.teelab.productcatalog.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.LikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UnlikeProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.CheckIfUserLikedProductQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetLikeCountByProductQuery;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductLikeService;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductLikeCountResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductLikeStatusResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.ProductUserLikeStatusResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.ProductLikeCountResourceAssembler;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.ProductLikeStatusResourceAssembler;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.ProductUserLikeStatusResourceAssembler;
import quri.teelab.api.teelab.shared.interfaces.rest.resources.ErrorResource;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Product Likes")
public class ProductLikesController {

    private final ProductLikeService productLikeService;

    public ProductLikesController(ProductLikeService productLikeService) {
        this.productLikeService = productLikeService;
    }

    @PostMapping("/{productId}/likes/{userId}")
    @Operation(
        summary = "Like a product", 
        description = "Create a like for a product by a specific user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like created successfully"),
            @ApiResponse(responseCode = "409", description = "Product already liked by user"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<?> likeProduct(
            @Parameter(description = "Product ID to like", required = true) 
            @PathVariable UUID productId,
            @Parameter(description = "User ID who wants to like", required = true)
            @PathVariable UUID userId) {
        try {
            // First validate that the user hasn't already liked this product
            var checkQuery = new CheckIfUserLikedProductQuery(productId, userId);
            Boolean isAlreadyLiked = productLikeService.handle(checkQuery);
            
            if (isAlreadyLiked) {
                // User has already liked this product
                var resource = ProductLikeStatusResourceAssembler.toAlreadyLikedResource(productId, userId);
                return ResponseEntity.status(409).body(resource);
            }
            
            // User hasn't liked yet, proceed with creation
            var command = new LikeProductCommand(productId, userId);
            productLikeService.handle(command);
            
            var resource = ProductLikeStatusResourceAssembler.toLikeSuccessResource(productId, userId);
            return ResponseEntity.status(201).body(resource);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            var errorResource = new ErrorResource("Internal server error: " + e.getMessage(), "Internal Server Error", 500);
            return ResponseEntity.internalServerError().body(errorResource);
        }
    }

    @DeleteMapping("/{productId}/likes/{userId}")
    @Operation(
        summary = "Unlike a product", 
        description = "Remove a like from a product by a specific user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Like removed successfully"),
            @ApiResponse(responseCode = "404", description = "Product or like not found")
    })
    public ResponseEntity<Void> unlikeProduct(
            @Parameter(description = "Product ID to unlike", required = true)
            @PathVariable UUID productId,
            @Parameter(description = "User ID who wants to unlike", required = true)
            @PathVariable UUID userId) {
        try {
            // First validate that the like exists for this user-product combination
            var checkQuery = new CheckIfUserLikedProductQuery(productId, userId);
            Boolean isLiked = productLikeService.handle(checkQuery);
            
            if (!isLiked) {
                // User hasn't liked this product, so nothing to delete
                return ResponseEntity.notFound().build();
            }
            
            // Like exists, proceed with deletion
            var command = new UnlikeProductCommand(productId, userId);
            productLikeService.handle(command);
            
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{productId}/likes/count")
    @Operation(summary = "Get like count", description = "Get the total number of likes for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like count retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductLikeCountResource> getLikeCount(
            @Parameter(description = "Product ID to get like count for", required = true)
            @PathVariable UUID productId) {
        try {
            var query = new GetLikeCountByProductQuery(productId);
            Long likeCount = productLikeService.handle(query);
            
            var resource = ProductLikeCountResourceAssembler.toResourceFromCount(likeCount);
            return ResponseEntity.ok(resource);
            
        } catch (Exception e) {
            var resource = ProductLikeCountResourceAssembler.toResourceFromCount(0L);
            return ResponseEntity.ok(resource);
        }
    }

    @GetMapping("/{productId}/likes/{userId}")
    @Operation(
        summary = "Check if user liked product", 
        description = "Check if a specific user has liked this product"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductUserLikeStatusResource> checkIfUserLiked(
            @Parameter(description = "Product ID to check", required = true)
            @PathVariable UUID productId,
            @Parameter(description = "User ID to check", required = true)
            @PathVariable UUID userId) {
        try {
            var query = new CheckIfUserLikedProductQuery(productId, userId);
            Boolean isLiked = productLikeService.handle(query);
            
            var resource = ProductUserLikeStatusResourceAssembler.toResourceFromStatus(isLiked);
            return ResponseEntity.ok(resource);
            
        } catch (Exception e) {
            var resource = ProductUserLikeStatusResourceAssembler.toResourceFromStatus(false);
            return ResponseEntity.ok(resource);
        }
    }
}
