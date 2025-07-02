package quri.teelab.api.teelab.usermanagement.domain.Model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import quri.teelab.api.teelab.usermanagement.domain.Model.commands.CreateProfileCommand;
import quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects.EmailAddress;
import quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects.PersonName;
import quri.teelab.api.teelab.usermanagement.domain.Model.valueobjects.StreetAddress;

/**
 * Profile Aggregate Root 
 * This class represents the Profile aggregate root.
 * It contains the properties and methods to manage the profile information.
 */
@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstName", column = @Column(name = "FirstName")),
        @AttributeOverride(name = "lastName", column = @Column(name = "LastName"))
    })
    private PersonName name;
    
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "EmailAddress"))
    private EmailAddress email;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "AddressStreet")),
        @AttributeOverride(name = "number", column = @Column(name = "AddressNumber")),
        @AttributeOverride(name = "city", column = @Column(name = "AddressCity")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "AddressPostalCode")),
        @AttributeOverride(name = "country", column = @Column(name = "AddressCountry"))
    })
    private StreetAddress address;
    
    public String getFullName() {
        return name != null ? name.getFullName() : "";
    }
    
    public String getEmailAddress() {
        return email != null ? email.getAddress() : "";
    }
    
    public String getStreetAddress() {
        return address != null ? address.getFullAddress() : "";
    }

    public Profile(String firstName, String lastName, String emailAddr, String street, String number, String city, String postalCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(emailAddr);
        this.address = new StreetAddress(street, number, city, postalCode, country);
    }

    public Profile(CreateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.address = new StreetAddress(command.street(), command.number(), command.city(), command.postalCode(), command.country());
    }
}