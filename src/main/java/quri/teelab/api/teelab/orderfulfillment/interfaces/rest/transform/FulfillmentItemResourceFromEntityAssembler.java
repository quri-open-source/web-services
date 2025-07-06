package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.transform;

import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources.FulfillmentItemResource;

public class FulfillmentItemResourceFromEntityAssembler {
    
    public static FulfillmentItemResource toResourceFromEntity(FulfillmentItem entity) {
        return new FulfillmentItemResource(
            entity.getId().toString(),
            entity.getProductId().toString(),
            entity.getQuantity(),
            entity.getStatus(),
            entity.getFulfillmentId()
        );
    }
}
