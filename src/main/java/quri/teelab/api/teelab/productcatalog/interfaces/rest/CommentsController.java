package quri.teelab.api.teelab.productcatalog.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CommentResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CreateCommentResource;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.transform.AddCommentCommandFromResourceAssembler;

import java.util.Date;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/products/{productId}/comments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Product Catalog")
public class CommentsController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public CommentsController(
            ProductCommandService productCommandService,
            ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @PostMapping
    @Operation(summary = "Add comment", description = "Add a new comment to a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment data")
    })
    public ResponseEntity<CommentResource> addComment(
            @PathVariable UUID productId,
            @RequestBody CreateCommentResource resource) {

        var command = AddCommentCommandFromResourceAssembler.toCommandFromResource(productId, resource);
        var commentId = productCommandService.handle(command);

        if (commentId == null) {
            return ResponseEntity.badRequest().build();
        }

        var commentResource = new CommentResource(commentId, resource.userId(), resource.text(), new Date());

        return new ResponseEntity<>(commentResource, HttpStatus.CREATED);
    }
}
