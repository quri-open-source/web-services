package quri.teelab.api.teelab.accesssecurity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;

import java.util.Optional;
import java.util.UUID;

/**
 * The user repository
 * This repository is used to manage users
 */
@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Find a user by username
     * @param username The username to search
     * @return The user
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user exists by username
     * @param username The username to search
     * @return True if the user exists, false otherwise
     */
    boolean existsByUsername(String username);
}