package quri.teelab.api.teelab.productcatalog.domain.model.valueobjects;

/**
 * Value object representing the status of a product in the catalog.
 * Defines the possible states a product can have in the system.
 */
public enum ProductStatus {
    /**
     * Product is available for purchase and display in the catalog.
     */
    AVAILABLE,
    
    /**
     * Product is temporarily unavailable but may become available again.
     */
    UNAVAILABLE,
    
    /**
     * Product is out of stock and cannot be purchased.
     */
    OUT_OF_STOCK,
    
    /**
     * Product has been discontinued and will not be restocked.
     */
    DISCONTINUED
}
