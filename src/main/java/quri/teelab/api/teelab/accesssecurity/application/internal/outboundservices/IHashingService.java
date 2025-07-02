package quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices;

/**
 * The hashing service interface
 * This interface is used to hash and verify passwords
 */
public interface IHashingService {
    
    /**
     * Hash a password
     * @param password The password to hash
     * @return The hashed password
     */
    String hashPassword(String password);

    /**
     * Verify a password
     * @param password The password to verify
     * @param passwordHash The password hash to verify against
     * @return True if the password is valid, false otherwise
     */
    boolean verifyPassword(String password, String passwordHash);
}