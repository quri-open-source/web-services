package quri.teelab.api.teelab.productcatalog.interfaces.acl;

import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ManufacturerId;
import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProjectId;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ProductCatalogContextFacade
 * <p>
 * This facade provides a clean, unified interface for other bounded contexts
 * to interact with the Product Catalog context without directly depending
 * on its internal domain model.
 */
public interface ProductCatalogContextFacade {

    /**
     * Create a new product
     *
     * @param projectId      The ID of the project this product is associated with
     * @param manufacturerId The ID of the manufacturer
     * @param price          The price amount
     * @param currency       The price currency
     * @param tags           List of product tags
     * @param gallery        List of image URLs for the product gallery
     * @param status         The product status
     * @return The created product ID
     */
    UUID createProduct(String projectId, String manufacturerId,
                       java.math.BigDecimal price, String currency,
                       List<String> tags, List<String> gallery,
                       String status);

    /**
     * Check if a product exists
     *
     * @param productId The ID of the product
     * @return True if the product exists, false otherwise
     */
    boolean productExists(UUID productId);

    /**
     * Get product details by ID
     *
     * @param productId The ID of the product
     * @return ProductInfo containing product details
     */
    Optional<ProductInfo> getProductInfo(UUID productId);

    /**
     * Get products by project ID
     *
     * @param projectId The project ID
     * @return List of product info for all products associated with the project
     */
    List<ProductInfo> getProductsByProject(String projectId);

    /**
     * Update product price
     *
     * @param productId The ID of the product
     * @param price     The new price amount
     * @param currency  The price currency
     * @return True if update was successful, false otherwise
     */
    boolean updateProductPrice(UUID productId, java.math.BigDecimal price, String currency);

    /**
     * Search products by tags
     *
     * @param tags List of tags to search for
     * @return List of matching product IDs
     */
    List<UUID> searchProductsByTags(List<String> tags);

    /**
     * Value object for transferring product information across bounded contexts
     */
    record ProductInfo(
            UUID id,
            ProjectId projectId,
            ManufacturerId manufacturerId,
            Money priceAmount,
            Currency currency,
            Integer likes,
            List<String> tags,
            List<String> gallery,
            Double rating,
            String status
    ) {
    }
}
