package quri.teelab.api.teelab.orderprocessing.domain.model.aggregates;

import quri.teelab.api.teelab.orderprocessing.domain.model.commands.CreateShoppingCartCommand;
import quri.teelab.api.teelab.orderprocessing.domain.model.entities.Item;
import quri.teelab.api.teelab.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Contiene la lista de ítems, el usuario asociado y el proyecto, así como métodos para manipular el carrito.
 */
@Entity
@Table(name = "shopping_carts", uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
public class ShoppingCart extends AuditableAbstractAggregateRoot<ShoppingCart> {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shopping_cart_id")
    private List<Item> items = new ArrayList<>();

    protected ShoppingCart() { }

    public ShoppingCart(UUID id, UUID userId) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.userId = Objects.requireNonNull(userId, "userId cannot be null");
    }

    public ShoppingCart(CreateShoppingCartCommand command) {
        this(command.id(), command.userId());
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public List<Item> getItems() { return Collections.unmodifiableList(items); }

    public void addItem(Item item) {
        items.add(Objects.requireNonNull(item, "item cannot be null"));
    }

    public void removeItem(UUID itemId) {
        items.removeIf(i -> i.getId().equals(itemId));
    }

    public int getTotalQuantity() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    public Map<UUID, BigDecimal> getTotalPricePerItem() {
        Map<UUID, BigDecimal> totalPerItem = new HashMap<>();
        for (Item item : items) {
            BigDecimal total = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPerItem.put(item.getId(), total);
        }
        return totalPerItem;
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    protected ShoppingCart self() {
        return this;
    }
}