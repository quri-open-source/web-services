package quri.teelab.api.teelab.accesssecurity.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import quri.teelab.api.teelab.accesssecurity.domain.services.IUserCommandService;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.AuthenticatedUserResource;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.SignInResource;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.resources.SignUpResource;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import quri.teelab.api.teelab.accesssecurity.interfaces.rest.transform.UserResourceFromEntityAssembler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication endpoints")
public class AuthenticationController {

    private final IUserCommandService userCommandService;

    public AuthenticationController(IUserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Sign in endpoint. It allows authenticating a user
     * @param signInResource The sign-in resource containing username and password.
     * @return The authenticated user resource, including a JWT token
     */
    @PostMapping("/sign-in")
    @Operation(
        summary = "Sign in",
        description = "Sign in a user"
    )
    @SecurityRequirements // Remove security requirement for this endpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        try {
            var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
            var authenticatedUser = userCommandService.handle(signInCommand);
            var resource = UserResourceFromEntityAssembler.toAuthenticatedResourceFromEntity(
                    authenticatedUser.user(), authenticatedUser.token());
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            System.err.println("Sign-in error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Sign up endpoint. It allows creating a new user
     * @param signUpResource The sign-up resource containing username and password.
     * @return A confirmation message on successful creation.
     */
    @PostMapping("/sign-up")
    @Operation(
        summary = "Sign-up",
        description = "Sign up a new user"
    )
    @SecurityRequirements // Remove security requirement for this endpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user was created"),
            @ApiResponse(responseCode = "400", description = "Username already exists"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    public ResponseEntity<String> signUp(@RequestBody SignUpResource signUpResource) {
        try {
            var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
            userCommandService.handle(signUpCommand);
            return ResponseEntity.status(201).body("User created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}