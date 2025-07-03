package quri.teelab.api.teelab.productcatalog.interfaces.acl;

import quri.teelab.api.teelab.productcatalog.domain.model.valueobjects.ProductStatus;
import quri.teelab.api.teelab.shared.domain.model.valueobjects.Money;

import java.math.BigDecimal;
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
     * Create a new product based on a project
     *
     * @param projectId      The ID of the project this product is associated with
     * @param price          The price amount
     * @param currency       The price currency
     * @param status         The product status
     * @return The created product ID
     */
    UUID createProduct(String projectId,
                       java.math.BigDecimal price, String currency,
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
     * Check if a project has any products
     *
     * @param projectId The project ID
     * @return True if project has products, false otherwise
     */
    boolean projectHasProducts(String projectId);

    /**
     * Value object for transferring product information across bounded contexts
     */
    record ProductInfo(
            UUID id,
            UUID projectId,
            Money price,
            ProductStatus status,
            String projectTitle,
            String projectPreviewUrl,
            UUID projectUserId,
            Long likeCount
    ) {
    }
}
