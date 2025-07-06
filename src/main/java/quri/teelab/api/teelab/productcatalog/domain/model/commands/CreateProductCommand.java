package quri.teelab.api.teelab.productcatalog.domain.model.commands;

import java.util.UUID;

import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;

public record CreateProductCommand(
        ProjectId projectId,
        Money price,
        ProductStatus status,
        String projectTitle,
        String projectPreviewUrl,
        UUID projectUserId,
        String garmentSize,
        String garmentGender,
        String garmentColor
) {
    /**
     * Alternative constructor accepting String IDs and primitive values
     */
    public CreateProductCommand(
            String projectId,
            Money price,
            ProductStatus status,
            String projectTitle,
            String projectPreviewUrl,
            UUID projectUserId,
            String garmentSize,
            String garmentGender,
            String garmentColor
    ) {
        this(
                ProjectId.of(projectId),
                price,
                status,
                projectTitle,
                projectPreviewUrl,
                projectUserId,
                garmentSize,
                garmentGender,
                garmentColor
        );
    }

    public CreateProductCommand {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (projectTitle == null || projectTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Project title cannot be null or empty");
        }
        if (projectUserId == null) {
            throw new IllegalArgumentException("Project user ID cannot be null");
        }
        if (garmentSize == null || garmentSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment size cannot be null or empty");
        }
        if (garmentGender == null || garmentGender.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment gender cannot be null or empty");
        }
        if (garmentColor == null || garmentColor.trim().isEmpty()) {
            throw new IllegalArgumentException("Garment color cannot be null or empty");
        }
    }
}
