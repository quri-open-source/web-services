package quri.teelab.api.teelab.usermanagement.application.acl;

import quri.teelab.api.teelab.usermanagement.domain.model.commands.CreateProfileCommand;
import quri.teelab.api.teelab.usermanagement.domain.model.queries.GetProfileByEmailQuery;
import quri.teelab.api.teelab.usermanagement.domain.model.valueobjects.EmailAddress;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileCommandService;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileQueryService;
import quri.teelab.api.teelab.usermanagement.interfaces.acl.IProfilesContextFacade;
import java.util.concurrent.CompletableFuture;

/**
 * Facade for the profiles context
 */
public class ProfilesContextFacade implements IProfilesContextFacade {
    
    private final IProfileCommandService profileCommandService;
    private final IProfileQueryService profileQueryService;
    
    /**
     * Constructor for ProfilesContextFacade
     * @param profileCommandService The profile command service
     * @param profileQueryService The profile query service
     */
    public ProfilesContextFacade(
            IProfileCommandService profileCommandService,
            IProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }
    
    @Override
    public CompletableFuture<Integer> createProfile(String firstName, String lastName, String email, 
            String street, String number, String city, String postalCode, String country) {
        CreateProfileCommand createProfileCommand = new CreateProfileCommand(firstName, lastName, email, 
                street, number, city, postalCode, country);
        return profileCommandService.handle(createProfileCommand)
                .thenApply(profileOpt -> profileOpt.map(profile -> profile.getId().intValue()).orElse(0));
    }

    @Override
    public CompletableFuture<Integer> fetchProfileIdByEmail(String email) {
        GetProfileByEmailQuery getProfileByEmailQuery = new GetProfileByEmailQuery(new EmailAddress(email));
        return profileQueryService.handle(getProfileByEmailQuery)
                .thenApply(profileOpt -> profileOpt.map(profile -> profile.getId().intValue()).orElse(0));
    }
}