package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

public record CreateImageLayerResource(
        String imageUrl,
        String width,
        String height
) {
}
