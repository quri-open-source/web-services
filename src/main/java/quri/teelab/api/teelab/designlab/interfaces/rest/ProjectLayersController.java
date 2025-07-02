package quri.teelab.api.teelab.designlab.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetLayerByIdQuery;
import quri.teelab.api.teelab.designlab.domain.services.LayerCommandService;
import quri.teelab.api.teelab.designlab.domain.services.LayerQueryService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateImageLayerResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateTextLayerResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateImageLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateTextLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.DeleteProjectLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.LayerResourceFromEntityAssembler;
import quri.teelab.api.teelab.shared.interfaces.rest.resources.ErrorResource;
import quri.teelab.api.teelab.shared.interfaces.rest.resources.SuccessMessage;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/projects/{projectId}", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Design Lab", description = "Available Project Layer Endpoints")
public class ProjectLayersController {
    private final ProjectCommandService projectCommandService;
    private final LayerQueryService layerQueryService;
    private final LayerCommandService layerCommandService;

    public ProjectLayersController(ProjectCommandService projectCommandService, LayerQueryService layerQueryService, LayerCommandService layerCommandService) {
        this.projectCommandService = projectCommandService;
        this.layerQueryService = layerQueryService;
        this.layerCommandService = layerCommandService;
    }

    private ResponseEntity<ErrorResource> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(
            new ErrorResource(message, status.getReasonPhrase(), status.value())
        );
    }

    @DeleteMapping("/layers/{layerId}")
    @Operation(summary = "Delete layer", description = "Delete a layer from the project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Layer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Layer or project not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteProjectLayerById(@PathVariable String projectId, @PathVariable String layerId) {
        try {
            // Validate input parameters
            if (projectId == null || projectId.trim().isEmpty()) {
                return createErrorResponse("Project ID cannot be null or empty", HttpStatus.BAD_REQUEST);
            }
            
            if (layerId == null || layerId.trim().isEmpty()) {
                return createErrorResponse("Layer ID cannot be null or empty", HttpStatus.BAD_REQUEST);
            }

            var deleteProjectLayerCommand = DeleteProjectLayerCommandFromResourceAssembler
                    .toCommandFromResource(projectId, layerId);

            var deletedLayerId = projectCommandService.handle(deleteProjectLayerCommand);

            if (deletedLayerId == null) {
                return createErrorResponse("Layer with ID '" + layerId + "' not found in project with ID '" + projectId + "'", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok().body(new SuccessMessage("Layer with ID " + deletedLayerId + " has been successfully deleted from project with ID " + projectId));
            
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid request - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return createErrorResponse("User not authorized to delete this layer - " + e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("not found")) {
                return createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Internal server error occurred while deleting layer - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/texts")
    @Operation(summary = "Create text layer", description = "Create a new text layer for the project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Text layer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createProjectTextLayer(@RequestBody CreateTextLayerResource resource) {
        try {
            // Validate input resource
            if (resource == null) {
                return createErrorResponse("Text layer resource cannot be null", HttpStatus.BAD_REQUEST);
            }

            var createTextLayerCommand = CreateTextLayerCommandFromResourceAssembler
                    .toCommandFromResource(resource);

            var createdLayerId = layerCommandService.handle(createTextLayerCommand);

            if (createdLayerId == null) {
                return createErrorResponse("Failed to create text layer. Please verify the input data and try again.", HttpStatus.BAD_REQUEST);
            }

            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return createErrorResponse("Created layer could not be retrieved. Layer ID: " + createdLayerId, HttpStatus.NOT_FOUND);
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid request data - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return createErrorResponse("User not authorized to create text layers - " + e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("project not found")) {
                return createErrorResponse("Project not found - " + e.getMessage(), HttpStatus.NOT_FOUND);
            }
            if (e.getMessage() != null && e.getMessage().contains("user not found")) {
                return createErrorResponse("User not found - " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Internal server error occurred while creating text layer - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/images")
    @Operation(summary = "Create image layer", description = "Create a new image layer for the project with provided image URL and dimensions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image layer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createProjectImageLayer(
            @RequestBody CreateImageLayerResource resource,
            @PathVariable String projectId) {
        
        try {
            // Validate input parameters
            if (projectId == null || projectId.trim().isEmpty()) {
                return createErrorResponse("Project ID cannot be null or empty", HttpStatus.BAD_REQUEST);
            }
            
            if (resource == null) {
                return createErrorResponse("Image layer resource cannot be null", HttpStatus.BAD_REQUEST);
            }
            
            // Validate required fields
            if (resource.imageUrl() == null || resource.imageUrl().trim().isEmpty()) {
                return createErrorResponse("Image URL is required and cannot be empty", HttpStatus.BAD_REQUEST);
            }
            
            if (resource.width() == null || resource.width().trim().isEmpty()) {
                return createErrorResponse("Width is required and cannot be empty", HttpStatus.BAD_REQUEST);
            }
            
            if (resource.height() == null || resource.height().trim().isEmpty()) {
                return createErrorResponse("Height is required and cannot be empty", HttpStatus.BAD_REQUEST);
            }
            
            // Validate numeric values for width and height
            try {
                double width = Double.parseDouble(resource.width());
                double height = Double.parseDouble(resource.height());
                
                if (width <= 0 || height <= 0) {
                    return createErrorResponse("Width and height must be positive numbers", HttpStatus.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return createErrorResponse("Width and height must be valid numbers", HttpStatus.BAD_REQUEST);
            }

            var createImageLayerCommand = CreateImageLayerCommandFromResourceAssembler
                    .toCommandFromResource(resource, projectId);

            var createdLayerId = layerCommandService.handle(createImageLayerCommand);

            if (createdLayerId == null) {
                return createErrorResponse("Failed to create image layer. Please verify the input data and try again.", HttpStatus.BAD_REQUEST);
            }
            
            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return createErrorResponse("Created image layer could not be retrieved. Layer ID: " + createdLayerId, HttpStatus.NOT_FOUND);
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return createErrorResponse("Invalid request data - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return createErrorResponse("User not authorized to create image layers - " + e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("project not found")) {
                return createErrorResponse("Project not found - " + e.getMessage(), HttpStatus.NOT_FOUND);
            }
            if (e.getMessage() != null && e.getMessage().contains("user not found")) {
                return createErrorResponse("User not found - " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            if (e.getMessage() != null && e.getMessage().contains("invalid url")) {
                return createErrorResponse("Invalid image URL - " + e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return createErrorResponse("Internal server error occurred while processing image layer - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
