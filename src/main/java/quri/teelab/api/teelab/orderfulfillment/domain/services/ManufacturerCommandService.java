package quri.teelab.api.teelab.orderfulfillment.domain.services;

import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Manufacturer;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateManufacturerCommand;

import java.util.Optional;

public interface ManufacturerCommandService {
    Optional<Manufacturer> handle(CreateManufacturerCommand command);
}
