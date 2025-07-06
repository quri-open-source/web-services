package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources;

public record CreateManufacturerResource(
        String userId,
        String name,
        String address,
        String city,
        String country,
        String state,
        String zip
) {
}
