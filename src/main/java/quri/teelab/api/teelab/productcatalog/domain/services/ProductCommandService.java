package quri.teelab.api.teelab.productcatalog.domain.services;

import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.DeleteProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductStatusCommand;

import java.util.UUID;

public interface ProductCommandService {
    UUID handle(CreateProductCommand command);
    
    void handle(UpdateProductPriceCommand command);
    
    void handle(UpdateProductStatusCommand command);
    
    void handle(DeleteProductCommand command);
}
