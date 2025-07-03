package quri.teelab.api.teelab.orderfulfillment.application.internal.commandservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsShippedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsReceivedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CancelFulfillmentItemCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemCommandService;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.FulfillmentItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FulfillmentItemCommandServiceImpl implements FulfillmentItemCommandService {

    private final FulfillmentItemRepository fulfillmentItemRepository;

    @Override
    public Optional<FulfillmentItem> handle(MarkFulfillmentItemAsShippedCommand command) {
        var fulfillmentItem = fulfillmentItemRepository.findById(command.fulfillmentItemId());
        
        if (fulfillmentItem.isEmpty()) {
            throw new IllegalArgumentException("FulfillmentItem does not exist");
        }
        
        var fulfillmentItemToShip = fulfillmentItem.get();
        fulfillmentItemToShip.markAsShipped();
        
        var updatedFulfillmentItem = fulfillmentItemRepository.save(fulfillmentItemToShip);
        return Optional.of(updatedFulfillmentItem);
    }

    @Override
    public Optional<FulfillmentItem> handle(MarkFulfillmentItemAsReceivedCommand command) {
        var fulfillmentItem = fulfillmentItemRepository.findById(command.fulfillmentItemId());
        
        if (fulfillmentItem.isEmpty()) {
            throw new IllegalArgumentException("FulfillmentItem does not exist");
        }
        
        var fulfillmentItemToReceive = fulfillmentItem.get();
        fulfillmentItemToReceive.markAsReceived();
        
        var updatedFulfillmentItem = fulfillmentItemRepository.save(fulfillmentItemToReceive);
        return Optional.of(updatedFulfillmentItem);
    }

    @Override
    public Optional<FulfillmentItem> handle(CancelFulfillmentItemCommand command) {
        var fulfillmentItem = fulfillmentItemRepository.findById(command.fulfillmentItemId());
        
        if (fulfillmentItem.isEmpty()) {
            throw new IllegalArgumentException("FulfillmentItem does not exist");
        }
        
        var fulfillmentItemToCancel = fulfillmentItem.get();
        fulfillmentItemToCancel.markAsCancelled();
        
        var updatedFulfillmentItem = fulfillmentItemRepository.save(fulfillmentItemToCancel);
        return Optional.of(updatedFulfillmentItem);
    }
}
