package quri.teelab.api.teelab.designlab.interfaces.rest.resources;

public record UpdateLayerCoordinatesResource(
        Integer x,
        Integer y,
        Integer z
) {
    public UpdateLayerCoordinatesResource {
        if (x == null) {
            throw new IllegalArgumentException("X coordinate cannot be null");
        }
        if (y == null) {
            throw new IllegalArgumentException("Y coordinate cannot be null");
        }
        if (z == null) {
            throw new IllegalArgumentException("Z coordinate cannot be null");
        }
    }
}
// TODO: ADD UPDATE IMAGE LAYER DETAILS 
// TODO: ADD UPDATE TEXT LAYER DETAILS