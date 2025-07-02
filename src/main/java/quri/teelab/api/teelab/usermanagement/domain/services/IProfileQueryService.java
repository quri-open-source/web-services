package quri.teelab.api.teelab.usermanagement.domain.services;

import quri.teelab.api.teelab.usermanagement.domain.Model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetAllProfilesQuery;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetProfileByEmailQuery;
import quri.teelab.api.teelab.usermanagement.domain.Model.queries.GetProfileByIdQuery;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile query service 
 */
public interface IProfileQueryService {
    
    /**
     * Handle get all profiles 
     * @param query The GetAllProfilesQuery query
     * @return A list of Profile objects
     */
    CompletableFuture<List<Profile>> handle(GetAllProfilesQuery query);
    
    /**
     * Handle get profile by email 
     * @param query The GetProfileByEmailQuery query
     * @return A Profile object or empty Optional
     */
    CompletableFuture<Optional<Profile>> handle(GetProfileByEmailQuery query);
    
    /**
     * Handle get profile by id 
     * @param query The GetProfileByIdQuery query
     * @return A Profile object or empty Optional
     */
    CompletableFuture<Optional<Profile>> handle(GetProfileByIdQuery query);
}