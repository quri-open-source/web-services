package quri.teelab.api.teelab.designlab.domain.model.queries;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record GetProjectDetailsForProductQuery(ProjectId projectId) {
    public GetProjectDetailsForProductQuery {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
    }
}
