package quri.teelab.api.teelab.designlab.domain.services;

import quri.teelab.api.teelab.designlab.domain.model.commands.CreateImageLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.CreateTextLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.DeleteProjectLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

import java.util.UUID;

public interface ProjectCommandService {
    LayerId handle(DeleteProjectLayerCommand command);

    ProjectId handle(CreateProjectCommand command);
}
