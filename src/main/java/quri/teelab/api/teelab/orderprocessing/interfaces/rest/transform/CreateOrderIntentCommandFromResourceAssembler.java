package quri.teelab.api.teelab.orderprocessing.interfaces.rest.transform;

import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateOrderIntentCommand;
import quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources.CreateOrderIntentResource;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

import java.util.Currency;

public class CreateOrderIntentCommandFromResourceAssembler {

    public static CreateOrderIntentCommand createCommandFromResource(CreateOrderIntentResource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Resource cannot be null");
        }

        if (resource.amount() == null) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        if (resource.currency() == null || resource.currency().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        var intentCurrency = Currency.getInstance(resource.currency());
        var amount = new Money(resource.amount(), intentCurrency);

        return new CreateOrderIntentCommand(amount);
    }
}
