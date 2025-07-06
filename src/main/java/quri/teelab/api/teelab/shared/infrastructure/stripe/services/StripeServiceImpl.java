package quri.teelab.api.teelab.shared.infrastructure.stripe.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.shared.infrastructure.stripe.StripeService;

@Service
public class StripeServiceImpl implements StripeService {
    public PaymentIntent createPaymentIntent(Money amount) throws StripeException {

        long amountInCents = Math.max(50L, (amount.amount().longValue() * 100));

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(amount.currency().getCurrencyCode())
                .build();
        return PaymentIntent.create(params);
    }
}
