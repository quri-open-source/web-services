package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.*;

import java.math.BigDecimal;
import java.util.Optional;

public interface ShoppingCartQueryService {
    Optional<ShoppingCart> handle(GetAllItemsInShoppingCartQuery query);
    boolean handle(ExistsItemInShoppingCartQuery query);
    BigDecimal handle(GetTotalPriceOfShoppingCartQuery query);

}