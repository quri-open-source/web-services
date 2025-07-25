package quri.teelab.api.teelab.productcatalog.interfaces.rest.transform;

import java.util.Currency;

import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.productcatalog.interfaces.rest.resources.CreateProductResource;
import quri.teelab.api.teelab.designlab.interfaces.acl.ProjectContextFacade;

/**
 * Transforms CreateProductResource DTOs into CreateProductCommand domain objects.
 * Part of the anti-corruption layer for the REST interface.
 */
public class CreateProductCommandFromResourceAssembler {

    public static CreateProductCommand toCommandFromResource(
            CreateProductResource resource, 
            ProjectContextFacade projectContextFacade) {
        
        // Get project details from DesignLab
        var projectDetails = projectContextFacade.fetchProjectDetailsForProduct(
            java.util.UUID.fromString(resource.projectId())
        );
        
        if (projectDetails == null) {
            throw new IllegalArgumentException("Project not found with ID: " + resource.projectId());
        }
        
        // Parse currency
        Currency currency = Currency.getInstance(
                resource.priceCurrency() != null ? resource.priceCurrency() : Money.DEFAULT_CURRENCY.getCurrencyCode()
        );
        
        return new CreateProductCommand(
                resource.projectId(),
                new Money(resource.priceAmount(), currency),
                ProductStatus.AVAILABLE,
                projectDetails.title(),
                projectDetails.previewUrl(),
                projectDetails.userId(),
                projectDetails.size(),
                projectDetails.gender(),
                projectDetails.color()
        );
    }
}
