package quri.teelab.api.teelab.orderfulfillment.domain.model.commands;

public record CreateManufacturerCommand(
        String userId,
        String name,
        String address,
        String city,
        String country,
        String state,
        String zip
) {
}
