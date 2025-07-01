package quri.teelab.api.teelab.designlab.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetLayerByIdQuery;
import quri.teelab.api.teelab.designlab.domain.services.LayerCommandService;
import quri.teelab.api.teelab.designlab.domain.services.LayerQueryService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectQueryService;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateImageLayerResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateTextLayerResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateImageLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateTextLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.DeleteProjectLayerCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.LayerResourceFromEntityAssembler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/projects/layers", produces = APPLICATION_JSON_VALUE)
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

    @DeleteMapping("/{projectId}/layers/{layerId}")
    public ResponseEntity<?> DeleteProjectLayerById(@PathVariable String projectId, @PathVariable String layerId) {
        var deleteProjectLayerCommand = DeleteProjectLayerCommandFromResourceAssembler.toCommandFromResource(projectId, layerId);

        var deletedLayerId = projectCommandService.handle(deleteProjectLayerCommand);

        if (deletedLayerId == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Layer with ID " + deletedLayerId + " has been successfully deleted from project with ID " + projectId);
    }


    @PostMapping(value = "/texts")
    public ResponseEntity<?> CreateProjectLayerText(@RequestBody CreateTextLayerResource resource) {
        var createTextLayerCommand = CreateTextLayerCommandFromResourceAssembler.ToCommandFromResource(resource);

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
    }

    @PostMapping(value = "/images")
    public ResponseEntity<?> CreateProjectLayerImage(@RequestBody CreateImageLayerResource resource) {
        var createImageLayerCommand = CreateImageLayerCommandFromResourceAssembler.ToCommandFromResource(resource);

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
    }
}
