package quri.teelab.api.teelab.usermanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Email address value object
 */
@Embeddable
@Getter
@NoArgsConstructor
public class EmailAddress {
    
    private String address = "";

    public EmailAddress(String address) {
        this.address = address != null ? address : "";
    }

    @Override
    public String toString() {
        return address;
    }
}
