package quri.teelab.api.teelab.orderfulfillment.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllManufacturersQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetManufacturerByUserIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.services.ManufacturerCommandService;
import quri.teelab.api.teelab.orderfulfillment.domain.services.ManufacturerQueryService;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.CreateManufacturerResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.ManufacturerResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.CreateManufacturerCommandFromResourceAssembler;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.GetManufacturerByUserIdQueryAssembler;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.ManufacturerResourceFromEntityAssembler;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/manufacturers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Manufacturers", description = "Available Manufacturer Endpoints")
public class ManufacturersController {

    private final ManufacturerQueryService manufacturerQueryService;
    private final ManufacturerCommandService manufacturerCommandService;

    public ManufacturersController(ManufacturerQueryService manufacturerQueryService,
                                   ManufacturerCommandService manufacturerCommandService) {
        this.manufacturerQueryService = manufacturerQueryService;
        this.manufacturerCommandService = manufacturerCommandService;
    }

    @GetMapping
    @Operation(summary = "Get all manufacturers", description = "Get all manufacturers")
    public ResponseEntity<?> getAllManufacturers() {
        var manufacturers = manufacturerQueryService.handle(new GetAllManufacturersQuery());
        if (manufacturers.isEmpty()) return ResponseEntity.notFound().build();
        var manufacturerResources = manufacturers.stream()
                .map(ManufacturerResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(manufacturerResources);
    }

    @GetMapping("/{userId}/details")
    @Operation(summary = "Get manufacturer by userId", description = "Get a manufacturer by user ID")
    public ResponseEntity<?> getManufacturerByUserId(@PathVariable String userId) {
        var query = GetManufacturerByUserIdQueryAssembler.toQueryFromUserId(userId);
        var manufacturer = manufacturerQueryService.handle(query);
        if (manufacturer.isEmpty()) return ResponseEntity.notFound().build();
        var manufacturerResource = ManufacturerResourceFromEntityAssembler.toResourceFromEntity(manufacturer.get());
        return ResponseEntity.ok(manufacturerResource);
    }


    @PostMapping
    @Operation(summary = "Create a manufacturer", description = "Create a new manufacturer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Manufacturer created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid data or manufacturer already exists for this user")
    })
    public ResponseEntity<ManufacturerResource> createManufacturer(@RequestBody CreateManufacturerResource resource) {
        var command = CreateManufacturerCommandFromResourceAssembler.toCommandFromResource(resource);
        var manufacturer = manufacturerCommandService.handle(command);

        if (manufacturer.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Manufacturer already exists or invalid data
        }

        var manufacturerResource = ManufacturerResourceFromEntityAssembler.toResourceFromEntity(manufacturer.get());
        return new ResponseEntity<>(manufacturerResource, HttpStatus.CREATED);
    }
}
