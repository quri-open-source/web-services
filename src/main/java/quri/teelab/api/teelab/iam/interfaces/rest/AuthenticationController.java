package quri.teelab.api.teelab.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quri.teelab.api.teelab.iam.domain.services.UserCommandService;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.AuthenticatedUserResource;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.SignInResource;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.SignUpResource;
import quri.teelab.api.teelab.iam.interfaces.rest.resources.UserResource;
import quri.teelab.api.teelab.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import quri.teelab.api.teelab.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import quri.teelab.api.teelab.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import quri.teelab.api.teelab.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    @Operation(summary = "Sign-in", description = "Sign-in with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    @Operation(summary = "Sign-up", description = "Sign-up with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request.")})
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }
}
