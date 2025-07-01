package quri.teelab.api.teelab.orderprocessing.domain.model.queries;

import java.util.UUID;

public record GetTotalPriceOfShoppingCartQuery(UUID shoppingCartId) {
    public GetTotalPriceOfShoppingCartQuery {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
    }
}