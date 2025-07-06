package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import com.stripe.model.PaymentIntent;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.OrderIntentResource;

public class CreateOrderIntentResourceFromEntityAssembler {

    public static OrderIntentResource toResourceFromEntity(
            PaymentIntent orderIntent) {
        return new OrderIntentResource(orderIntent.getClientSecret());
    }
}
