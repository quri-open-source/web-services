package quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.configuration.extensions;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Configuration Extensions for Order Processing context
 * This class configures JPA entities and repositories for the Order Processing module
 */
@Configuration("orderProcessingModelBuilderExtensions")
@EnableJpaRepositories(basePackages = "quri.teelab.api.teelab.orderprocessing.infrastructure.persistence.jpa.repositories")
@EntityScan(basePackages = {
    "quri.teelab.api.teelab.orderprocessing.domain.model.aggregates",
    "quri.teelab.api.teelab.orderprocessing.domain.model.entities"
})
public class ModelBuilderExtensions {

    /**
     * Apply Order Processing configuration for JPA entities
     * Note: In Spring Boot/JPA, entity configuration is done through annotations
     * directly on the entity classes rather than through a separate configuration.
     * 
     * The order processing entities should have the following JPA annotations:
     * @Entity - to mark it as a JPA entity
     * @Table - to specify the table name
     * @EmbeddedId - to mark the composite key
     * @Column - to map fields to columns
     * 
     * This configuration enables:
     * 1. Repository scanning for OrderProcessingRepository
     * 2. Entity scanning for order processing entities
     * 3. Proper JPA configuration for the order processing bounded context
     */
}
