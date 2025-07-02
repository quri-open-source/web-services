package quri.teelab.api.teelab.accesssecurity.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetAllUsersQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByIdQuery;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserQueryService;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.UserResource;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform.UserResourceFromEntityAssembler;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The user's controller
 * This class is used to handle user requests
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Available User endpoints")
public class UsersController {

    private final IUserQueryService userQueryService;

    public UsersController(IUserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    /**
     * Get user by id endpoint. It allows to get a user by id
     * @param id The user id
     * @return The user resource
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Get a user by its id",
        description = "Get a user by its id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was found"),
            @ApiResponse(responseCode = "404", description = "The user was not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<UserResource> getUserById(@PathVariable UUID id) {
        var getUserByIdQuery = new GetUserByIdQuery(id);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    /**
     * Get all users' endpoint. It allows getting all users
     * @return The user resources
     */
    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Get all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The users were found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }
}