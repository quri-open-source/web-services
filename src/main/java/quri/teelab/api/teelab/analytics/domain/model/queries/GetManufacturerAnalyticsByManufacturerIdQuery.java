package quri.teelab.api.teelab.analytics.domain.model.queries;

import java.util.UUID;

public class GetManufacturerAnalyticsByManufacturerIdQuery {
    private final UUID manufacturerId;

    public GetManufacturerAnalyticsByManufacturerIdQuery(UUID manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public UUID getManufacturerId() {
        return manufacturerId;
    }
}
