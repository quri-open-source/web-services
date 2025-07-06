package quri.teelab.api.teelab.orderfulfillment.domain.services;

import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsShippedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsReceivedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CancelFulfillmentItemCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.UpdateFulfillmentItemStatusCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;

import java.util.Optional;

public interface FulfillmentItemCommandService {
    Optional<FulfillmentItem> handle(UpdateFulfillmentItemStatusCommand command);
}
