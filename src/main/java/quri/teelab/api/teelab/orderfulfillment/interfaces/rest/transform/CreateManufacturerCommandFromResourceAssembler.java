package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateManufacturerCommand;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.CreateManufacturerResource;

public class CreateManufacturerCommandFromResourceAssembler {
    public static CreateManufacturerCommand toCommandFromResource(CreateManufacturerResource resource) {
        return new CreateManufacturerCommand(
                resource.userId(),
                resource.name(),
                resource.address(),
                resource.city(),
                resource.country(),
                resource.state(),
                resource.zip()
        );
    }
}
