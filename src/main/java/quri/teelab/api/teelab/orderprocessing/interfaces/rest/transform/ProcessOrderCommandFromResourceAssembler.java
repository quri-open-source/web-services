package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.commands.ProcessOrderCommand;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.ProcessOrderResource;

import java.util.UUID;

public class ProcessOrderCommandFromResourceAssembler {

    public static ProcessOrderCommand toCommand(ProcessOrderResource resource) {
        return new ProcessOrderCommand(
                resource.orderId(),
                resource.paymentMethod()
        );
    }
}
