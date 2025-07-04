package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record DeleteProjectCommand(ProjectId projectId) {
    public DeleteProjectCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
    }
}
