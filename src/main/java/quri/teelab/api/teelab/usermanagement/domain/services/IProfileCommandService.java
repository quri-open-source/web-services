package quri.teelab.api.teelab.usermanagement.domain.services;

import quri.teelab.api.teelab.usermanagement.domain.Model.aggregates.Profile;
import quri.teelab.api.teelab.usermanagement.domain.Model.commands.CreateProfileCommand;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Profile command service interface 
 */
public interface IProfileCommandService {
    
    /**
     * Handle create profile command 
     * @param command The CreateProfileCommand command
     * @return The Profile object with the created profile
     */
    CompletableFuture<Optional<Profile>> handle(CreateProfileCommand command);
}