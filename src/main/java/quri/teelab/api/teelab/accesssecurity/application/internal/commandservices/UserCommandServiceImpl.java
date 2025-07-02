package quri.teelab.api.teelab.accesssecurity.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.IHashingService;
import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.ITokenService;
import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignInCommand;
import quri.teelab.api.teelab.accesssecurity.domain.model.Commands.SignUpCommand;
import quri.teelab.api.teelab.accesssecurity.domain.repositories.IUserRepository;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserCommandService;

/**
 * User command service implementation
 */
@Service
public class UserCommandServiceImpl implements IUserCommandService {

    private final IUserRepository userRepository;
    private final IHashingService hashingService;
    private final ITokenService tokenService;

    public UserCommandServiceImpl(IUserRepository userRepository, 
                                IHashingService hashingService,
                                ITokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticatedUser handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!hashingService.verifyPassword(command.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        var token = tokenService.generateToken(user);
        return new IUserCommandService.AuthenticatedUser(user, token);
    }

    @Override
    public User handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username())) {
            throw new RuntimeException("Username already exists");
        }

        var hashedPassword = hashingService.hashPassword(command.password());
        var user = new User(command.username(), hashedPassword);
        return userRepository.save(user);
    }
}
