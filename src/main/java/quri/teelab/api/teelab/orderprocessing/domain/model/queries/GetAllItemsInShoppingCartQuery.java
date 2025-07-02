package quri.teelab.api.teelab.orderprocessing.domain.model.queries;

import java.util.UUID;

public record GetAllItemsInShoppingCartQuery(UUID shoppingCartId) {
    public GetAllItemsInShoppingCartQuery {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
    }
}