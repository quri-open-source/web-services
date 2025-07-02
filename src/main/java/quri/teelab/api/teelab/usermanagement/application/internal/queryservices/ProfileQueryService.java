package quri.teelab.api.teelab.usermanagement.application.internal.queryservices;

import quri.teelab.api.teelab.usermanagement.domain.Model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetAllProfilesQuery;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetProfileByEmailQuery;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetProfileByIdQuery;
import quri.teelab.api.teelab.usermanagement.domain.repositories.IProfileRepository;
import quri.teelab.api.teelab.usermanagement.domain.services.IProfileQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile query service implementation
 */
@Service
public class ProfileQueryService implements IProfileQueryService {
    
    private final IProfileRepository profileRepository;
    
    /**
     * Constructor for ProfileQueryService
     * @param profileRepository The profile repository
     */
    public ProfileQueryService(IProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    
    /**
     * Handle get all profiles query
     * @param query The GetAllProfilesQuery query
     * @return A list of all profiles
     */
    @Override
    public CompletableFuture<List<Profile>> handle(GetAllProfilesQuery query) {
        return CompletableFuture.supplyAsync(() -> profileRepository.findAll());
    }

    /**
     * Handle get profile by email query
     * @param query The GetProfileByEmailQuery query
     * @return The profile with the specified email, or empty Optional if not found
     */
    @Override
    public CompletableFuture<Optional<Profile>> handle(GetProfileByEmailQuery query) {
        return profileRepository.findProfileByEmailAsync(query.email());
    }

    /**
     * Handle get profile by id query
     * @param query The GetProfileByIdQuery query
     * @return The profile with the specified id, or empty Optional if not found
     */
    @Override
    public CompletableFuture<Optional<Profile>> handle(GetProfileByIdQuery query) {
        return CompletableFuture.supplyAsync(() -> profileRepository.findById(query.profileId()));
    }
}