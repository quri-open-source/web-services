package quri.teelab.api.teelab.designlab.domain.services;

import quri.teelab.api.teelab.designlab.domain.model.commands.*;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.LayerId;

public interface LayerCommandService {

    LayerId handle(CreateTextLayerCommand command);

    LayerId handle(CreateImageLayerCommand command);

    LayerId handle(UpdateLayerCoordinatesCommand command);

    LayerId handle(UpdateTextLayerDetailsCommand command);

    LayerId handle(UpdateImageLayerDetailsCommand command);
}
