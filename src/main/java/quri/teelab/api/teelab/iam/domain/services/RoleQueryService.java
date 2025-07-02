package quri.teelab.api.teelab.iam.domain.services;

import quri.teelab.api.teelab.iam.domain.model.entities.Role;
import quri.teelab.api.teelab.iam.domain.model.queries.GetAllRolesQuery;
import quri.teelab.api.teelab.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

/**
 * Role query service
 * <p>
 *     This interface represents the service that handles the role queries.
 * </p>
 */
public interface RoleQueryService {
    /**
     * Handle get all roles query
     * @param query the {@link GetAllRolesQuery} query
     * @return a list of {@link Role} entities
     */
    List<Role> handle(GetAllRolesQuery query);

    /**
     * Handle get role by name query
     * @param query the {@link GetRoleByNameQuery} query
     * @return an {@link Optional} of {@link Role} entity
     */
    Optional<Role> handle(GetRoleByNameQuery query);
}
