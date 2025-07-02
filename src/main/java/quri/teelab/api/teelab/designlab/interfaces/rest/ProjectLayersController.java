package quri.teelab.api.teelab.designlab.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetLayerByIdQuery;
import quri.teelab.api.teelab.designlab.domain.services.LayerCommandService;
import quri.teelab.api.teelab.designlab.domain.services.LayerQueryService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateTextLayerResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateImageLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateTextLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.DeleteProjectLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.LayerResourceFromEntityAssembler;

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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Project ID cannot be null or empty");
            }
            
            if (layerId == null || layerId.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Layer ID cannot be null or empty");
            }

            var deleteProjectLayerCommand = DeleteProjectLayerCommandFromResourceAssembler
                    .toCommandFromResource(projectId, layerId);

            var deletedLayerId = projectCommandService.handle(deleteProjectLayerCommand);

            if (deletedLayerId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Layer with ID '" + layerId + "' not found in project with ID '" + projectId + "'");
            }

            return ResponseEntity.ok("Layer with ID " + deletedLayerId + " has been successfully deleted from project with ID " + projectId);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid request - " + e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: User not authorized to delete this layer - " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Internal server error occurred while deleting layer - " + e.getMessage());
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Text layer resource cannot be null");
            }

            var createTextLayerCommand = CreateTextLayerCommandFromResourceAssembler
                    .toCommandFromResource(resource);

            var createdLayerId = layerCommandService.handle(createTextLayerCommand);

            if (createdLayerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Failed to create text layer. Please verify the input data and try again.");
            }

            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Created layer could not be retrieved. Layer ID: " + createdLayerId);
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid request data - " + e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: User not authorized to create text layers - " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("project not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Project not found - " + e.getMessage());
            }
            if (e.getMessage() != null && e.getMessage().contains("user not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User not found - " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Internal server error occurred while creating text layer - " + e.getMessage());
        }
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    @Operation(summary = "Create image layer", description = "Create a new image layer for the project. Image dimensions are calculated automatically.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image layer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or unsupported image format"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "413", description = "File too large"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createProjectImageLayer(
            @RequestParam("imageFile") MultipartFile imageFile, 
            @PathVariable String projectId) {
        
        try {
            // Validate input parameters
            if (projectId == null || projectId.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Project ID cannot be null or empty");
            }
            
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Image file is required and cannot be empty");
            }
            
            // Validate file type
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: File must be an image. Received content type: " + contentType);
            }
            
            // Validate file size (example: max 10MB)
            long maxFileSize = 10 * 1024 * 1024; // 10MB
            if (imageFile.getSize() > maxFileSize) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("Error: File size too large. Maximum allowed size is 10MB. Received: " + (imageFile.getSize() / 1024 / 1024) + "MB");
            }

            var createImageLayerCommand = CreateImageLayerCommandFromResourceAssembler
                    .toCommandFromResource(imageFile, projectId);

            var createdLayerId = layerCommandService.handle(createImageLayerCommand);

            if (createdLayerId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Failed to create image layer. Please verify the image file and try again.");
            }
            
            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Created image layer could not be retrieved. Layer ID: " + createdLayerId);
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Invalid request data - " + e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: User not authorized to create image layers - " + e.getMessage());
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("project not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Project not found - " + e.getMessage());
            }
            if (e.getMessage() != null && e.getMessage().contains("user not found")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User not found - " + e.getMessage());
            }
            if (e.getMessage() != null && e.getMessage().contains("unsupported format")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unsupported image format - " + e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Internal server error occurred while processing image layer - " + e.getMessage());
        }
    }
}
