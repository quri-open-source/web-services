package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

/**
 * Resource representation of an Address.
 * Note: We use primitive types at the API boundary layer instead of value objects
 * as part of the anti-corruption layer pattern.
 */

public record AddressResource(
        String address,
        String city,
        String country,
        String state,
        String zip
) {
}
