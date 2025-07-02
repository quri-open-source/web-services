package quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import quri.teelab.api.teelab.orderprocessing.domain.model.aggregates.ShoppingCart;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    boolean existsByUserId(UUID userId);
    Optional<ShoppingCart> findByUserId(UUID userId);
}