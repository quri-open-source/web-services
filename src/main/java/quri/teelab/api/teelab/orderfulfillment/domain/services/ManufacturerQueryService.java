package quri.teelab.api.teelab.orderfulfillment.domain.services;

import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Manufacturer;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllManufacturersQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetManufacturerByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ManufacturerQueryService {
    List<Manufacturer> handle(GetAllManufacturersQuery query);
    Optional<Manufacturer> handle(GetManufacturerByUserIdQuery query);
}
