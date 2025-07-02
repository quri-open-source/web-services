package quri.teelab.api.teelab.accesssecurity.domain.model.Queries;

import java.util.UUID;

/**
 * The get user by id query
 * This query object includes the user id to search
 */
public record GetUserByIdQuery(UUID id) {
}