package quri.teelab.api.teelab.usermanagement.infrastructure.persistence.jpa.configuration.extensions;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Configuration for User Management bounded context
 * This class configures JPA entity scanning and repository scanning for Profile entities.
 */
@Configuration("userManagementModelBuilderExtensions")
@EntityScan(basePackages = "quri.teelab.api.teelab.usermanagement.domain.Model.aggregates")
@EnableJpaRepositories(basePackages = "quri.teelab.api.teelab.usermanagement.infrastructure.persistence.jpa.repositories")
public class ModelBuilderExtensions {
    
    /**
     * Note: In Spring Boot/JPA, entity configuration is done through annotations on the entity classes.
     * The Profile entity class should have the following JPA annotations:
     * 
     * @Entity
     * @Table(name = "profiles")
     * @AttributeOverrides for embedded value objects (PersonName, EmailAddress, StreetAddress)
     * @Embedded annotations for value objects
     * @Column annotations for custom column mappings
     * 
     * Example mappings for embedded objects:
     * - PersonName: firstName -> FirstName, lastName -> LastName
     * - EmailAddress: address -> EmailAddress
     * - StreetAddress: street -> AddressStreet, number -> AddressNumber, etc.
     */
}