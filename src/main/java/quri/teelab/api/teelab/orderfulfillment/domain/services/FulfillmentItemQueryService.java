package quri.teelab.api.teelab.orderfulfillment.domain.services;

import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.model.queries.GetAllFulfillmentItemsByFulfillmentIdQuery;

import java.util.List;

public interface FulfillmentItemQueryService {
    List<FulfillmentItem> handle(GetAllFulfillmentItemsByFulfillmentIdQuery query);
}
