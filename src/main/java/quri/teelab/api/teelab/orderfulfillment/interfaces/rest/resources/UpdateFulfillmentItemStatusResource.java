package quri.teelab.api.teelab.orderfulfillment.interfaces.rest.resources;

import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;

public record UpdateFulfillmentItemStatusResource(String updateTo) {
}
