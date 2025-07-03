package quri.teelab.api.teelab.productcatalog.interfaces.rest.resources;

public record ProductLikeCountResource(
        Long likeCount
) {
    public ProductLikeCountResource {
        if (likeCount == null || likeCount < 0) {
            throw new IllegalArgumentException("Like count cannot be null or negative");
        }
    }
}
