package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

public record ProductUserLikeStatusResource(
        Boolean isLiked
) {
    public ProductUserLikeStatusResource {
        if (isLiked == null) {
            throw new IllegalArgumentException("Like status cannot be null");
        }
    }
}
