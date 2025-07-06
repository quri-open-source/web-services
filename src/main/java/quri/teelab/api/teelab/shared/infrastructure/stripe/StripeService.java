package quri.teelab.api.teelab.shared.infrastructure.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

public interface StripeService {
    public PaymentIntent createPaymentIntent(Money amount) throws StripeException;
}
