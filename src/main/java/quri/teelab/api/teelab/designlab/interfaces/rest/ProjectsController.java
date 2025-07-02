package quri.teelab.api.teelab.designlab.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetAllProjectsByUserIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectByIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectQueryService;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateProjectResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.ProjectResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateProjectCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.ProjectResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/projects", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Projects", description = "Available Project Endpoints")
public class ProjectsController {
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectsController(ProjectCommandService projectCommandService, ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    @GetMapping
    @Operation(summary = "Get projects", description = "Get all projects, optionally filtered by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found"),
            @ApiResponse(responseCode = "404", description = "No projects found")
    })
    public ResponseEntity<List<ProjectResource>> getAllProjects(@RequestParam(value = "userId", required = false) String userId) {
        if (userId != null) {
            var getAllProjectsByUserIdQuery = new GetAllProjectsByUserIdQuery(UserId.of(userId));
            var projects = projectQueryService.handle(getAllProjectsByUserIdQuery);
            
            if (projects.isEmpty()) return ResponseEntity.notFound().build();
            
            var projectsResource = projects.stream()
                    .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(projectsResource);
        }
        
        // TODO: Implement GetAllProjectsQuery for when no userId is provided
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by ID", description = "Get a specific project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ProjectResource> getProjectById(@PathVariable String projectId) {
        var getProjectByIdQuery = new GetProjectByIdQuery(ProjectId.of(projectId));
        var project = projectQueryService.handle(getProjectByIdQuery);

        if (project == null) return ResponseEntity.notFound().build();

        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project);
        return ResponseEntity.ok(projectResource);
    }

    @PostMapping()
    @Operation(summary = "Create project", description = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ProjectResource> createProject(@RequestBody CreateProjectResource resource) {
        System.out.println("Received CreateProjectResource: ");
        var createProjectCommand = CreateProjectCommandFromResourceAssembler.CreateProjectCommandFromResourceAssembler(resource);
        var projectId = projectCommandService.handle(createProjectCommand);

        if (projectId == null) return ResponseEntity.badRequest().build();
        
        var getProjectByIdQuery = new GetProjectByIdQuery(projectId);
        var project = projectQueryService.handle(getProjectByIdQuery);

        if (project == null) return ResponseEntity.notFound().build();

        var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project);
        return new ResponseEntity<>(projectResource, HttpStatus.CREATED);
    }
}
