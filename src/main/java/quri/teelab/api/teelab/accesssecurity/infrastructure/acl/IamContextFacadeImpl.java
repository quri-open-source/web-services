package quri.teelab.api.teelab.accesssecurity.infrastructure.acl;

import org.springframework.stereotype.Service;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignUpCommand;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByIdQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByUsernameQuery;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserCommandService;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserQueryService;
import quri.teelab.api.teelab.accesssecurity.interfaces.acl.services.IIamContextFacade;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the IAM Context Facade.
 * Provides access to IAM operations for other bounded contexts.
 */
@Service
public class IamContextFacadeImpl implements IIamContextFacade {
    
    private final IUserCommandService userCommandService;
    private final IUserQueryService userQueryService;
    
    public IamContextFacadeImpl(IUserCommandService userCommandService, 
                               IUserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }
    
    @Override
    public Optional<UUID> createUser(String username, String password) {
        try {
            var command = new SignUpCommand(username, password);
            var user = userCommandService.handle(command);
            return Optional.of(user.getId());
        } catch (Exception e) {
            // Log the exception if needed
            return Optional.empty();
        }
    }
    
    @Override
    public Optional<UUID> fetchUserIdByUsername(String username) {
        var query = new GetUserByUsernameQuery(username);
        var userOptional = userQueryService.handle(query);
        return userOptional.map(User::getId);
    }
    
    @Override
    public Optional<String> fetchUsernameByUserId(UUID userId) {
        var query = new GetUserByIdQuery(userId);
        var userOptional = userQueryService.handle(query);
        return userOptional.map(User::getUsername);
    }
}
