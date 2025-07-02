package quri.teelab.api.teelab.usermanagement.domain.model.queries;

import quri.teelab.api.teelab.usermanagement.domain.model.valueobjects.EmailAddress;

/**
 * Query to get a profile by email
 */
public record GetProfileByEmailQuery(EmailAddress email) {
}
