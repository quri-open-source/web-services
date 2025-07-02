package quri.teelab.api.teelab.accesssecurity.domain.services;

import java.util.List;
import java.util.Optional;

import quri.teelab.api.teelab.accesssecurity.domain.model.Aggregates.User;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetAllUsersQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByIdQuery;
import quri.teelab.api.teelab.accesssecurity.domain.model.Queries.GetUserByUsernameQuery;

/**
 * The user query service interface
 * This service contract specifies handling behavior used to query users
 */
public interface IUserQueryService {
    
    /**
     * Handle get user by id query
     * @param query The get user by id query
     * @return The user if found, empty otherwise
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get all users query
     * @param query The get all users query
     * @return The list of users
     */
    List<User> handle(GetAllUsersQuery query);
    
    /**
     * Handle get user by username query
     * @param query The get user by username query
     * @return The user if found, empty otherwise
     */
    Optional<User> handle(GetUserByUsernameQuery query);
}