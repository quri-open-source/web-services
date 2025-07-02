package quri.teelab.api.teelab.accesssecurity.infrastructure.persistence.jpa.configuration.extensions;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Configuration Extensions for IAM (Identity and Access Management) context
 * This class configures JPA entities and repositories for the Access Security module
 */
@Configuration("accessSecurityModelBuilderExtensions")
@EnableJpaRepositories(basePackages = "quri.teelab.api.teelab.accesssecurity.infrastructure.persistence.jpa.repositories")
@EntityScan(basePackages = "quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates")
public class ModelBuilderExtensions {

    /**
     * Apply IAM configuration for JPA entities
     * Note: In Spring Boot/JPA, entity configuration is done through annotations
     * directly on the entity classes rather than through a separate configuration.
     * 
     * The User entity is configured with the following JPA annotations:
     * - @Entity: Marks the class as a JPA entity
     * - @Table(name = "users"): Specifies the table name
     * - @Id: Marks the primary key field
     * - @GeneratedValue(strategy = GenerationType.IDENTITY): Auto-generated ID
     * - @Column(unique = true, nullable = false): Username constraints
     * - @Column(nullable = false): Password hash constraints
     * 
     * This configuration ensures:
     * 1. User entity has a primary key (Id) that is auto-generated
     * 2. Username field is required and unique
     * 3. PasswordHash field is required
     * 4. Proper table mapping and constraints
     */
    
    // In Java/Spring Boot, entity configuration is handled by annotations on the entity classes
    // The equivalent configuration is already applied in the User entity class:
    //
    // @Entity
    // @Table(name = "users")
    // public class User extends UserAudit {
    //     @Id
    //     @GeneratedValue(strategy = GenerationType.IDENTITY)
    //     private Long id;
    //     
    //     @Column(unique = true, nullable = false)
    //     private String username;
    //     
    //     @JsonIgnore
    //     @Column(nullable = false)
    //     private String passwordHash;
    // }
}