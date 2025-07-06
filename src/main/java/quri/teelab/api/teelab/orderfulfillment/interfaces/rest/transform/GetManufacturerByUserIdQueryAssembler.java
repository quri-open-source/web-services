package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetManufacturerByUserIdQuery;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.UserId;

import java.util.UUID;

public class GetManufacturerByUserIdQueryAssembler {

    public static GetManufacturerByUserIdQuery toQueryFromUserId(String userId) {
        return new GetManufacturerByUserIdQuery(userId == null ? null : new UserId(UUID.fromString(userId)));
    }
}
