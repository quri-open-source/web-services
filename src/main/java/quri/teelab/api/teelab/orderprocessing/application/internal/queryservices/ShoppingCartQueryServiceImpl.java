package quri.teelab.api.teelab.orderprocessing.application.internal.queryservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;
import quri.teelab.api.teelab.orderprocessing.domain.model.queries.*;
import quri.teelab.api.teelab.orderprocessing.domain.services.ShoppingCartQueryService;
import quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShoppingCartQueryServiceImpl implements ShoppingCartQueryService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartQueryServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public Optional<ShoppingCart> handle(GetAllItemsInShoppingCartQuery query) {
        return shoppingCartRepository.findById(query.shoppingCartId());
    }

    @Override
    public boolean handle(ExistsItemInShoppingCartQuery query) {
        return shoppingCartRepository.findById(query.shoppingCartId())
                .map(cart -> cart.getItems().stream()
                        .anyMatch(item -> item.getId().equals(query.itemId())))
                .orElse(false);
    }

    @Override
    public BigDecimal handle(GetTotalPriceOfShoppingCartQuery query) {
        return shoppingCartRepository.findById(query.shoppingCartId())
                .map(ShoppingCart::getTotalPrice)
                .orElse(BigDecimal.ZERO);
    }

    public Optional<ShoppingCart> findByUserId(UUID userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

}