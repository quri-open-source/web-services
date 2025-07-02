package quri.teelab.api.teelab.accesssecurity.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.repositories.IUserRepository;

import java.util.Optional;

/**
 * The user repository
 * This repository is used to manage users
 */
@Repository
public interface UserRepository extends IUserRepository {
    
    /**
     * Find a user by username
     * @param username The username to search
     * @return The user if found
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
    
    /**
     * Check if a user exists by username
     * @param username The username to search
     * @return True if the user exists, false otherwise
     */
    boolean existsByUsername(String username);
}