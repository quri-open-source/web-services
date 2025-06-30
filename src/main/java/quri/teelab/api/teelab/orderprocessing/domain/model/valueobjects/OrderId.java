package quri.teelab.api.teelab.orderprocessing.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

/**
 * Value Object para el identificador de OrderProcessing.
 * Similar al OrderId de orderfulfillment, se almacena como un embebido.
 */

@Embeddable
public record OrderId(UUID orderId) {

    public OrderId {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
    }

    /**
     * Crea un OrderId a partir de un UUID existente.
     */
    public static OrderId of(UUID id) {
        return new OrderId(id);
    }

    /**
     * Genera un nuevo OrderId con UUID aleatorio.
     */
    public static OrderId random() {
        return new OrderId(UUID.randomUUID());
    }
}
