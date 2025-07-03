package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import java.util.Currency;
import java.util.UUID;

import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CreateProductResource;

/**
 * Transforms CreateProductResource DTOs into CreateProductCommand domain objects.
 * Part of the anti-corruption layer for the REST interface.
 */
public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(CreateProductResource resource, UUID manufacturerId) {
        Currency currency = Currency.getInstance(
                resource.currency() != null ? resource.currency() : Money.DEFAULT_CURRENCY.getCurrencyCode()
        );

        return new CreateProductCommand(
                ProjectId.of(resource.projectId()),
                ManufacturerId.of(manufacturerId.toString()),
                new Money(resource.price(), currency),
                resource.tags(),
                resource.gallery(),
                resource.status()
        );
    }
}
