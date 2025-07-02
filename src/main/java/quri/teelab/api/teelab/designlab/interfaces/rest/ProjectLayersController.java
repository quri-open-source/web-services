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
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> deleteProjectLayerById(@PathVariable String projectId, @PathVariable String layerId) {
        try {
            var deleteProjectLayerCommand = DeleteProjectLayerCommandFromResourceAssembler
                    .toCommandFromResource(projectId, layerId);

            var deletedLayerId = projectCommandService.handle(deleteProjectLayerCommand);

            if (deletedLayerId == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok("Layer with ID " + deletedLayerId + " has been successfully deleted from project with ID " + projectId);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }


    @PostMapping(value = "/texts")
    @Operation(summary = "Create text layer", description = "Create a new text layer for the project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Text layer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<?> createProjectTextLayer(@RequestBody CreateTextLayerResource resource) {
        try {
            var createTextLayerCommand = CreateTextLayerCommandFromResourceAssembler
                    .toCommandFromResource(resource);

            var createdLayerId = layerCommandService.handle(createTextLayerCommand);

            if (createdLayerId == null) {
                return ResponseEntity.badRequest().body("Failed to create text layer.");
            }

            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return ResponseEntity.notFound().build();
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    @Operation(summary = "Create image layer", description = "Create a new image layer for the project. Image dimensions are calculated automatically.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image layer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or unsupported image format"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createProjectImageLayer(
            @RequestParam("imageFile") MultipartFile imageFile, 
            @PathVariable String projectId) {
        
        try {
            var createImageLayerCommand = CreateImageLayerCommandFromResourceAssembler
                    .toCommandFromResource(imageFile, projectId);

            var createdLayerId = layerCommandService.handle(createImageLayerCommand);

            if (createdLayerId == null) {
                return ResponseEntity.badRequest().body("Failed to create image layer.");
            }
            
            var getLayerByIdQuery = new GetLayerByIdQuery(createdLayerId);
            var layer = layerQueryService.handle(getLayerByIdQuery);

            if (layer == null) {
                return ResponseEntity.notFound().build();
            }

            var layerResource = LayerResourceFromEntityAssembler.toResourceFromEntity(layer);
            return new ResponseEntity<>(layerResource, HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }
}
