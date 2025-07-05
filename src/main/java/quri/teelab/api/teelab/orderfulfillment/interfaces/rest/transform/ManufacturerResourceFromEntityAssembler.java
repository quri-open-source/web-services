package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Manufacturer;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.ManufacturerResource;

public class ManufacturerResourceFromEntityAssembler {

    public static ManufacturerResource toResourceFromEntity(Manufacturer entity) {
        return new ManufacturerResource(
                entity.getId().toString(),
                entity.getUserId().toString(),
                entity.getName(),
                entity.getAddress().toString(),
                entity.getAddress().city(),
                entity.getAddress().country(),
                entity.getAddress().state(),
                entity.getAddress().zip(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
