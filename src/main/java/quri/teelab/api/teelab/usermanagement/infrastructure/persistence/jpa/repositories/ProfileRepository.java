package quri.teelab.api.teelab.usermanagement.infrastructure.persistence.jpa.repositories;

import quri.teelab.api.teelab.usermanagement.domain.Model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects.EmailAddress;
import quri.teelab.api.teelab.usermanagement.domain.repositories.IProfileRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile repository implementation  
 */
@Repository
public interface ProfileRepository extends IProfileRepository {
    
    /**
     * Find a profile by email address
     * @param email the email address to search for
     * @return the profile if found, otherwise empty Optional
     */
    @Query("SELECT p FROM Profile p WHERE p.email.address = :emailAddress")
    CompletableFuture<Optional<Profile>> findProfileByEmailAsync(@Param("emailAddress") String emailAddress);
    
    /**
     * Find a profile by email value object
     * @param email the EmailAddress value object to search for
     * @return the profile if found, otherwise empty Optional
     */
    default CompletableFuture<Optional<Profile>> findProfileByEmailAsync(EmailAddress email) {
        return findProfileByEmailAsync(email.getAddress());
    }
}