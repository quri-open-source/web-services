package quri.teelab.api.teelab.shared.interfaces.rest.resources;

import java.time.LocalDateTime;

public record SuccessMessage(
        String message,
        LocalDateTime timestamp
) {
    public SuccessMessage(String message) {
        this(message, LocalDateTime.now());
    }
}
