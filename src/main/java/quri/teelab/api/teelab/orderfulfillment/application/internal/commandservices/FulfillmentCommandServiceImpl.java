package quri.teelab.api.teelab.orderfulfillment.application.internal.commandservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Fulfillment;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateFulfillmentCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.FulfillmentItemStatus;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.ProductId;
import quri.teelab.api.teelab.orderfulfillment.domain.services.FulfillmentCommandService;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.FulfillmentRepository;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.ManufacturerRepository;
import quri.teelab.api.teelab.orderprocessing.interfaces.acl.OrderProcessingContextFacade;

import java.util.UUID;

@Service
public class FulfillmentCommandServiceImpl implements FulfillmentCommandService {

    private final FulfillmentRepository fulfillmentRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final OrderProcessingContextFacade orderProcessingContextFacade;

    public FulfillmentCommandServiceImpl(
            FulfillmentRepository fulfillmentRepository,
            ManufacturerRepository manufacturerRepository,
            OrderProcessingContextFacade orderProcessingContextFacade) {
        this.fulfillmentRepository = fulfillmentRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.orderProcessingContextFacade = orderProcessingContextFacade;
    }

    @Override
    @Transactional
    public UUID handle(CreateFulfillmentCommand command) {
        // Validate that orderId exists in order-processing bounded context
        if (!orderProcessingContextFacade.orderExists(command.orderId().value())) {
            throw new EntityNotFoundException("Order with ID " + command.orderId().value() + " not found");
        }

        // Validate that manufacturerId exists
        var manufacturer = manufacturerRepository.findById(command.manufacturerId().value())
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer with ID " + command.manufacturerId().value() + " not found"));

        // Check for duplicate fulfillments (same orderId and manufacturerId)
        if (fulfillmentRepository.existsByOrderIdAndManufacturer_Id(command.orderId(), manufacturer.getId())) {
            throw new IllegalArgumentException("A fulfillment already exists for orderId " + command.orderId().value() + " and manufacturerId " + manufacturer.getId());
        }

        // Create the fulfillment aggregate
        var fulfillment = new Fulfillment(command, manufacturer);

        // Fetch items from order-processing bounded context using ACL
        var items = orderProcessingContextFacade.fetchItemsByOrderId(command.orderId().value());

        // Create FulfillmentItems for each item found
        for (var itemDto : items) {
            fulfillment.createItem(
                    new ProductId(itemDto.productId()),
                    itemDto.quantity(),
                    FulfillmentItemStatus.PENDING
            );
        }

        // Save the fulfillment (with items due to CASCADE)
        var savedFulfillment = fulfillmentRepository.save(fulfillment);
        return savedFulfillment.getId();
    }
}
