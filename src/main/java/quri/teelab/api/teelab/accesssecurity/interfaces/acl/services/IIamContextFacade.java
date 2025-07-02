package quri.teelab.api.teelab.accesssecurity.interfaces.acl.services;

import java.util.Optional;
import java.util.UUID;

/**
 * IAM Context Facade Interface
 * This interface provides access to IAM (Identity and Access Management) operations
 * for other bounded contexts that need to interact with user management functionality.
 */
public interface IIamContextFacade {
    
    /**
     * Creates a new user with the specified username and password
     * @param username The username for the new user
     * @param password The password for the new user
     * @return Optional containing the ID of the created user if successful, empty otherwise
     */
    Optional<UUID> createUser(String username, String password);
    
    /**
     * Fetches a user ID by username
     * @param username The username to search for
     * @return Optional containing the user ID if found, empty otherwise
     */
    Optional<UUID> fetchUserIdByUsername(String username);
    
    /**
     * Fetches a username by user ID
     * @param userId The user ID to search for
     * @return Optional containing the username if found, empty otherwise
     */
    Optional<String> fetchUsernameByUserId(UUID userId);
}