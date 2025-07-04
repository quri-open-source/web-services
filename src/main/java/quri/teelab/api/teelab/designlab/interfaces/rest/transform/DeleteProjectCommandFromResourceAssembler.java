package quri.teelab.api.teelab.designlab.interfaces.rest.transform;

import quri.teelab.api.teelab.designlab.domain.model.commands.DeleteProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public class DeleteProjectCommandFromResourceAssembler {
    public static DeleteProjectCommand toCommandFromResource(String projectId) {
        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("Project ID cannot be null or blank");
        }

        return new DeleteProjectCommand(ProjectId.of(projectId));
    }
}
