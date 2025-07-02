package quri.teelab.api.teelab.accesssecurity.application.internal.queryservices;

import org.springframework.stereotype.Service;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetAllUsersQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByIdQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByUsernameQuery;
import quri.teelab.api.teelab.accesssecurity.domain.repositories.IUserRepository;
import quri.teelab.api.teelab.accesssecurity.domain.services.IUserQueryService;

import java.util.List;
import java.util.Optional;

/**
 * User query service implementation
 */
@Service
public class UserQueryServiceImpl implements IUserQueryService {

    private final IUserRepository userRepository;

    public UserQueryServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id());
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}
