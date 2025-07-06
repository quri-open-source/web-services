package quri.teelab.api.teelab.orderfulfillment.application.internal.commandservices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsShippedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.MarkFulfillmentItemAsReceivedCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CancelFulfillmentItemCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.UpdateFulfillmentItemStatusCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.entities.FulfillmentItem;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentItemCommandService;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.FulfillmentItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FulfillmentItemCommandServiceImpl implements FulfillmentItemCommandService {

    private final FulfillmentItemRepository fulfillmentItemRepository;

    @Override
    public Optional<FulfillmentItem> handle(UpdateFulfillmentItemStatusCommand command) {
        var fulfillmentItem = fulfillmentItemRepository.findById(command.fulfillmentItemId());
        if (fulfillmentItem.isEmpty()) {
            throw new IllegalArgumentException("FulfillmentItem does not exist");
        }
        var fulfillmentItemToUpdate = fulfillmentItem.get();
        var currentStatus = fulfillmentItemToUpdate.getStatus();
        var newStatus = command.updateTo();

        // Validaciones de transición de estado
        switch (currentStatus) {
            case PENDING:
                if (newStatus != FulfillmentItemStatus.SHIPPED && newStatus != FulfillmentItemStatus.CANCELLED) {
                    throw new IllegalStateException("PENDING items can only be updated to SHIPPED or CANCELLED");
                }
                break;
            case SHIPPED:
                if (newStatus != FulfillmentItemStatus.RECEIVED) {
                    throw new IllegalStateException("SHIPPED items can only be updated to RECEIVED");
                }
                break;
            case RECEIVED:
            case CANCELLED:
                throw new IllegalStateException("Cannot update status from RECEIVED or CANCELLED");
            default:
                throw new IllegalStateException("Unknown status");
        }

        fulfillmentItemToUpdate.updateStatus(newStatus);
        var updatedFulfillmentItem = fulfillmentItemRepository.save(fulfillmentItemToUpdate);
        return Optional.of(updatedFulfillmentItem);
    }
}
