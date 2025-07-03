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

import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> likeProduct(
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
                return ResponseEntity.status(409).body(Map.of(
                    "message", "Product already liked by user",
                    "productId", productId,
                    "userId", userId
                ));
            }
            
            // User hasn't liked yet, proceed with creation
            var command = new LikeProductCommand(productId, userId);
            productLikeService.handle(command);
            
            return ResponseEntity.status(201).body(Map.of(
                "message", "Product liked successfully",
                "productId", productId,
                "userId", userId
            ));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(Map.of("error", "Internal server error: " + e.getMessage()));
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
    public ResponseEntity<Map<String, Long>> getLikeCount(
            @Parameter(description = "Product ID to get like count for", required = true)
            @PathVariable UUID productId) {
        try {
            var query = new GetLikeCountByProductQuery(productId);
            Long likeCount = productLikeService.handle(query);
            
            return ResponseEntity.ok(Map.of("likeCount", likeCount));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("likeCount", 0L));
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
    public ResponseEntity<Map<String, Boolean>> checkIfUserLiked(
            @Parameter(description = "Product ID to check", required = true)
            @PathVariable UUID productId,
            @Parameter(description = "User ID to check", required = true)
            @PathVariable UUID userId) {
        try {
            var query = new CheckIfUserLikedProductQuery(productId, userId);
            Boolean isLiked = productLikeService.handle(query);
            
            return ResponseEntity.ok(Map.of("isLiked", isLiked));
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("isLiked", false));
        }
    }
}
