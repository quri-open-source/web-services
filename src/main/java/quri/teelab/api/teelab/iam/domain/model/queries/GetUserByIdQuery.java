package quri.teelab.api.teelab.iam.domain.model.queries;

import java.util.UUID;

/**
 * Get user by id query
 * <p>
 *     This class represents the query to get a user by its id.
 * </p>
 * @param userId the id of the user
 */
public record GetUserByIdQuery(UUID userId) {
}
