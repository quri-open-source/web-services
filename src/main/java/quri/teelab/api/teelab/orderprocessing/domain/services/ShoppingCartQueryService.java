package quri.teelab.api.teelab.orderprocessing.domain.services;

import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartQueryService {
    Optional<ShoppingCart> handle(GetAllItemsInShoppingCartQuery query);
    boolean handle(ExistsItemInShoppingCartQuery query);
    BigDecimal handle(GetTotalPriceOfShoppingCartQuery query);

}