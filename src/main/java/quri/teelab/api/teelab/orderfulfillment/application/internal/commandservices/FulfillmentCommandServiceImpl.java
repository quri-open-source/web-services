package quri.teelab.api.teelab.orderfulfillment.application.internal.commandservices;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
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

    public FulfillmentCommandServiceImpl(FulfillmentRepository fulfillmentRepository, 
                                       ManufacturerRepository manufacturerRepository,
                                       OrderProcessingContextFacade orderProcessingContextFacade) {
        this.fulfillmentRepository = fulfillmentRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.orderProcessingContextFacade = orderProcessingContextFacade;
    }

    @Override
    public UUID handle(CreateFulfillmentCommand command) {
        // 1. Buscar el manufacturer por ID
        var manufacturer = manufacturerRepository.findById(command.manufacturerId().value())
            .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found: " + command.manufacturerId().value()));
        
        // 2. Crear el fulfillment usando el constructor correcto con manufacturer
        var fulfillment = new Fulfillment(command, manufacturer);
        
        // 3. Obtener items del order usando ACL
        var items = orderProcessingContextFacade.fetchItemsByOrderId(command.orderId().value());
        
        // 4. Crear fulfillment items usando el método del agregado
        for (var item : items) {
            fulfillment.createItem(
                new ProductId(item.projectId()),
                item.quantity(),
                FulfillmentItemStatus.PENDING
            );
        }
        
        // 5. Guardar fulfillment con cascada automática de items
        fulfillmentRepository.save(fulfillment);
        
        return fulfillment.getId();
    }
}
