package quri.teelab.api.teelab.orderprocessing.domain.model.queries;

import java.util.UUID;

public record ExistsItemInShoppingCartQuery(UUID shoppingCartId, UUID itemId) {
    public ExistsItemInShoppingCartQuery {
        if (shoppingCartId == null) throw new IllegalArgumentException("shoppingCartId cannot be null");
        if (itemId == null) throw new IllegalArgumentException("itemId cannot be null");
    }
}