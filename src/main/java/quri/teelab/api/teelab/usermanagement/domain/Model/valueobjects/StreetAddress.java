package quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Street address value object
 */
@Embeddable
@Getter
@NoArgsConstructor
public class StreetAddress {
    
    private String street = "";
    private String number = "";
    private String city = "";
    private String postalCode = "";
    private String country = "";

    public StreetAddress(String street) {
        this.street = street != null ? street : "";
    }

    public StreetAddress(String street, String number, String city, String postalCode) {
        this.street = street != null ? street : "";
        this.number = number != null ? number : "";
        this.city = city != null ? city : "";
        this.postalCode = postalCode != null ? postalCode : "";
    }

    public StreetAddress(String street, String number, String city, String postalCode, String country) {
        this.street = street != null ? street : "";
        this.number = number != null ? number : "";
        this.city = city != null ? city : "";
        this.postalCode = postalCode != null ? postalCode : "";
        this.country = country != null ? country : "";
    }

    public String getFullAddress() {
        return String.format("%s %s, %s, %s, %s", street, number, city, postalCode, country).trim();
    }
}