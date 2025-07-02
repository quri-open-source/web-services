package quri.teelab.api.teelab.usermanagement.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.usermanagement.domain.model.queries.GetProfileByIdQuery;
import quri.teelab.api.teelab.usermanagement.domain.model.queries.GetAllProfilesQuery;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileCommandService;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileQueryService;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources.CreateProfileResource;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Resources.ProfileResource;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Transform.CreateProfileCommandFromResourceAssembler;
import quri.teelab.api.teelab.usermanagement.interfaces.rest.Transform.ProfileResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Available Profile Endpoints")
public class ProfilesController {
    
    private final IProfileCommandService profileCommandService;
    private final IProfileQueryService profileQueryService;
    
    public ProfilesController(IProfileCommandService profileCommandService, IProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }
    
    @GetMapping("/{profileId}")
    @Operation(summary = "Get Profile by Id", description = "Get a profile by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The profile was found and returned"),
        @ApiResponse(responseCode = "404", description = "The profile was not found")
    })
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profileFuture = profileQueryService.handle(getProfileByIdQuery);
        
        try {
            var profileOptional = profileFuture.get();
            if (profileOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileOptional.get());
            return ResponseEntity.ok(profileResource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    @Operation(summary = "Create Profile", description = "Create a new profile")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The profile was created"),
        @ApiResponse(responseCode = "400", description = "The profile was not created")
    })
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource resource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var profileFuture = profileCommandService.handle(createProfileCommand);
        
        try {
            var profileOptional = profileFuture.get();
            if (profileOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileOptional.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(profileResource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Get All Profiles", description = "Get all profiles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The profiles were found and returned"),
        @ApiResponse(responseCode = "404", description = "The profiles were not found")
    })
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profilesFuture = profileQueryService.handle(getAllProfilesQuery);
        
        try {
            var profiles = profilesFuture.get();
            var profileResources = profiles.stream()
                .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
            return ResponseEntity.ok(profileResources);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}