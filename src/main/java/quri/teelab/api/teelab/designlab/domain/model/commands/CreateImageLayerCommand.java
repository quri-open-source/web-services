package quri.teelab.api.teelab.designlab.domain.model.commands;

import quri.teelab.api.teelab.designlab.domain.model.valueobjects.ProjectId;

import java.io.File;

public record CreateImageLayerCommand
        (ProjectId projectId, File imageUrl, Float width, Float height)
{
}
