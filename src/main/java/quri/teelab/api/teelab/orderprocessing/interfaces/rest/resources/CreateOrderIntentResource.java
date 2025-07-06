package quri.teelab.api.teelab.orderprocessing.interfaces.rest.resources;

import java.math.BigDecimal;

public record CreateOrderIntentResource(BigDecimal amount, String currency) {
    public CreateOrderIntentResource {
        if (amount == null) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
    }
}
