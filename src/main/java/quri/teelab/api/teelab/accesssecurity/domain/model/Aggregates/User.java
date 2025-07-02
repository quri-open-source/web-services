package quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The user aggregate
 * This class is used to represent a user
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends UserAudit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @JsonIgnore
    @Column(nullable = false)
    private String passwordHash;
    
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /**
     * Update the username
     * @param username The new username
     * @return The updated user
     */
    public User updateUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Update the password hash
     * @param passwordHash The new password hash
     * @return The updated user
     */
    public User updatePasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }
}