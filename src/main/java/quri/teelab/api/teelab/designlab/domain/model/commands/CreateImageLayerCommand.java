package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

public record CreateImageLayerCommand
        (ProjectId projectId, String imageUrl, Float width, Float height)
{
}
