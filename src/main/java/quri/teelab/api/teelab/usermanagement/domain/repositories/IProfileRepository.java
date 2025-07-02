package quri.teelab.api.teelab.usermanagement.domain.repositories;

import quri.teelab.api.teelab.usermanagement.domain.model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.model.valueobjects.EmailAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile repository interface 
 */
public interface IProfileRepository extends JpaRepository<Profile, Long> {
    
    /**
     * Find a profile by email 
     * @param email The EmailAddress email address to search for
     * @return The Profile if found, otherwise empty Optional
     */
    CompletableFuture<Optional<Profile>> findProfileByEmailAsync(EmailAddress email);
}