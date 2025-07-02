package quri.teelab.api.teelab.usermanagement.application.internal.commandservices;

import quri.teelab.api.teelab.usermanagement.domain.model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.model.commands.CreateProfileCommand;
import quri.teelab.api.teelab.usermanagement.domain.repositories.IProfileRepository;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileCommandService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile command service implementation
 */
@Service
public class ProfileCommandService implements IProfileCommandService {
    
    private final IProfileRepository profileRepository;
    
    /**
     * Constructor for ProfileCommandService
     * @param profileRepository The profile repository
     */
    public ProfileCommandService(IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    /**
     * Handle create profile command
     * @param command The CreateProfileCommand command
     * @return The created Profile wrapped in Optional, or empty Optional if creation failed
     */
    @Override
    public CompletableFuture<Optional<Profile>> handle(CreateProfileCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Profile profile = new Profile(command);
                Profile savedProfile = profileRepository.save(profile);
                return Optional.of(savedProfile);
            } catch (Exception e) {
                // Log error - in production, use proper logging framework
                System.err.println("Error creating profile: " + e.getMessage());
                return Optional.empty();
            }
        });
    }
}