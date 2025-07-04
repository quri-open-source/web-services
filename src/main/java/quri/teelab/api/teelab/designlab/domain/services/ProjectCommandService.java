package quri.teelab.api.teelab.designlab.domain.services;

import quri.teelab.api.teelab.designlab.domain.model.commands.CreateProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.DeleteProjectCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.DeleteProjectLayerCommand;
import quri.teelab.api.teelab.designlab.domain.model.commands.UpdateProductDetailsCommand;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;


public interface ProjectCommandService {
    LayerId handle(DeleteProjectLayerCommand command);

    ProjectId handle(CreateProjectCommand command);
    ProjectId handle(UpdateProductDetailsCommand command);
    
    void handle(DeleteProjectCommand command);
}
