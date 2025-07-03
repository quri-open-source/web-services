package quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Manufacturer;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.UserId;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {
    Optional<Manufacturer> findByUserId(UserId userId);
    boolean existsByUserId(UserId userId);
}
