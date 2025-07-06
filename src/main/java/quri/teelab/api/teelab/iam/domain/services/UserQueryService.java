package quri.teelab.api.teelab.iam.domain.services;

import quri.teelab.api.teelab.iam.domain.model.aggregates.User;
import quri.teelab.api.teelab.iam.domain.model.queries.GetAllUsersQuery;
import quri.teelab.api.teelab.iam.domain.model.queries.GetUserByIdQuery;
import quri.teelab.api.teelab.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

/**
 * User query service
 * <p>
 *     This interface represents the service to handle user queries.
 * </p>
 */
public interface UserQueryService {
    /**
     * Handle get all users query
     * @param query the {@link GetAllUsersQuery} query
     * @return a list of {@link User} entities
     */
    List<User> handle(GetAllUsersQuery query);

    /**
     * Handle get user by id query
     * @param query the {@link GetUserByIdQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get user by username query
     * @param query the {@link GetUserByUsernameQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByUsernameQuery query);

}
