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
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectDetailsForProductQuery;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.designlab.domain.services.ProjectCommandService;
import quri.teelab.api.teelab.designlab.domain.services.ProjectQueryService;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.CreateProjectResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.ProjectDetailsResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.resources.ProjectResource;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.CreateProjectCommandFromResourceAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.ProjectDetailsResourceFromEntityAssembler;
import quri.teelab.api.teelab.designlab.interfaces.rest.transform.ProjectResourceFromEntityAssembler;
import quri.teelab.api.teelab.shared.interfaces.rest.resources.ErrorResource;
import quri.teelab.api.teelab.shared.interfaces.rest.resources.SuccessMessage;

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

    private ResponseEntity<ErrorResource> errorResponse(String message, HttpStatus status) {
        var error = new ErrorResource(message, status.getReasonPhrase(), status.value());
        return ResponseEntity.status(status).body(error);
    }

    @GetMapping("")
    @Operation(summary = "Get projects", description = "Get all projects, optionally filtered by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects found"),
            @ApiResponse(responseCode = "404", description = "No projects found")
    })
    public ResponseEntity<?> getAllProjects(@RequestParam(value = "userId", required = false) String userId) {
        if (userId != null) {
            System.out.println("Received userId: " + userId);
            try {
                var getAllProjectsByUserIdQuery = new GetAllProjectsByUserIdQuery(UserId.of(userId));
                var projects = projectQueryService.handle(getAllProjectsByUserIdQuery);
                
                if (projects.isEmpty()) {
                    return errorResponse("No projects found for user ID: " + userId, HttpStatus.NOT_FOUND);
                }
                
                var projectsResource = projects.stream()
                        .map(ProjectResourceFromEntityAssembler::toResourceFromEntity)
                        .toList();
                return ResponseEntity.ok(projectsResource);
            } catch (Exception e) {
                return errorResponse("Invalid user ID format: " + userId, HttpStatus.BAD_REQUEST);
            }
        }
        
        // TODO: Implement GetAllProjectsQuery for when no userId is provided
        return errorResponse("Getting all projects without userId filter is not yet implemented", HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by ID", description = "Get a specific project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
        try {
            var getProjectByIdQuery = new GetProjectByIdQuery(ProjectId.of(projectId));
            var project = projectQueryService.handle(getProjectByIdQuery);

            if (project == null) {
                return errorResponse("Project not found with ID: " + projectId, HttpStatus.NOT_FOUND);
            }

            var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project);
            return ResponseEntity.ok(projectResource);
        } catch (Exception e) {
            return errorResponse("Invalid project ID format: " + projectId, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    @Operation(summary = "Create project", description = "Create a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createProject(@RequestBody CreateProjectResource resource) {
        try {
            System.out.println("Received CreateProjectResource: ");
            var createProjectCommand = CreateProjectCommandFromResourceAssembler.CreateProjectCommandFromResourceAssembler(resource);
            var projectId = projectCommandService.handle(createProjectCommand);

            if (projectId == null) {
                return errorResponse("Failed to create project. Please check your input data.", HttpStatus.BAD_REQUEST);
            }
            
            var getProjectByIdQuery = new GetProjectByIdQuery(projectId);
            var project = projectQueryService.handle(getProjectByIdQuery);

            if (project == null) {
                return errorResponse("Project created but could not be retrieved", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            var projectResource = ProjectResourceFromEntityAssembler.toResourceFromEntity(project);
            return new ResponseEntity<>(projectResource, HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse("Error creating project: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{projectId}/details")
    @Operation(summary = "Get project details for product", description = "Get essential project details needed for product catalog integration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "400", description = "Invalid project ID format")
    })
    public ResponseEntity<?> getProjectDetailsForProduct(@PathVariable String projectId) {
        try {
            var query = new GetProjectDetailsForProductQuery(ProjectId.of(projectId));
            var project = projectQueryService.handle(query);

            if (project == null) {
                return errorResponse("Project not found with ID: " + projectId, HttpStatus.NOT_FOUND);
            }

            var projectDetailsResource = ProjectDetailsResourceFromEntityAssembler.toResourceFromEntity(project);
            return ResponseEntity.ok(projectDetailsResource);
        } catch (IllegalArgumentException e) {
            return errorResponse("Invalid project ID format: " + projectId, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("Error retrieving project details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
