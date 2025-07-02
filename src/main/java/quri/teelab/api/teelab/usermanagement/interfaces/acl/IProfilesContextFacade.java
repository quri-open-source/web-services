package quri.teelab.api.teelab.usermanagement.interfaces.acl;

import java.util.concurrent.CompletableFuture;

/**
 * Facade for the profiles context
 */
public interface IProfilesContextFacade {
    
    /**
     * Create a profile
     * 
     * @param firstName First name of the profile
     * @param lastName Last name of the profile
     * @param email Email of the profile
     * @param street Street of the profile
     * @param number Number of the profile
     * @param city City of the profile
     * @param postalCode Postal code of the profile
     * @param country Country of the profile
     * @return The id of the created profile if successful, 0 otherwise
     */
    CompletableFuture<Integer> createProfile(String firstName, 
        String lastName, 
        String email, 
        String street, 
        String number,
        String city,
        String postalCode,
        String country);
    
    /**
     * Fetch the profile id by email
     * 
     * @param email Email of the profile to fetch
     * @return The id of the profile if found, 0 otherwise
     */
    CompletableFuture<Integer> fetchProfileIdByEmail(String email);
}