package quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Person name value object
 */
@Embeddable
@Getter
@NoArgsConstructor
public class PersonName {
    
    private String firstName = "";
    private String lastName = "";

    public PersonName(String firstName, String lastName) {
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName).trim();
    }

    @Override
    public String toString() {
        return getFullName();
    }
}