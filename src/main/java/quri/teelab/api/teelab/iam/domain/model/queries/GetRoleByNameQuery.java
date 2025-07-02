package quri.teelab.api.teelab.iam.domain.model.queries;

import quri.teelab.api.teelab.iam.domain.model.valueobjects.Roles;

/**
 * Get role by name query
 * <p>
 *     This class represents the query to get a role by its name.
 * </p>
 * @param name the name of the role
 * @see Roles
 */
public record GetRoleByNameQuery(Roles name) {
}
