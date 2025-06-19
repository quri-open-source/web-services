package quri.teelab.api.teelab.productcatalog.domain.services;

import quri.teelab.api.teelab.productcatalog.domain.model.commands.AddCommentCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;

import java.util.UUID;

public interface ProductCommandService {
    UUID handle(CreateProductCommand command);

    void handle(UpdateProductPriceCommand command);

    UUID handle(AddCommentCommand command);
}
