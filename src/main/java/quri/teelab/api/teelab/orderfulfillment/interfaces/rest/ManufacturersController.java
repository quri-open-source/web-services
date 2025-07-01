package quri.teelab.api.teelab.orderfulfillment.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllManufacturersQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.services.ManufacturerQueryService;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.ManufacturerResource;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform.ManufacturerResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/manufacturers", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Manufacturers", description = "Available Manufacturer Endpoints")
public class ManufacturersController {

    private final ManufacturerQueryService manufacturerQueryService;

    public ManufacturersController(ManufacturerQueryService manufacturerQueryService) {
        this.manufacturerQueryService = manufacturerQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all manufacturers", description = "Get all manufacturers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Manufacturers found"), @ApiResponse(responseCode = "404", description = "Manufacturers not found")})
    public ResponseEntity<List<ManufacturerResource>> getAllManufacturers() {
        var manufacturers = manufacturerQueryService.handle(new GetAllManufacturersQuery());
        if (manufacturers.isEmpty()) return ResponseEntity.notFound().build();
        var manufacturerResources = manufacturers.stream().map(ManufacturerResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(manufacturerResources);
    }
}
