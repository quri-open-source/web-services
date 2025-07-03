package quri.teelab.api.teelab.productcatalog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.CreateProductCommand;
import quri.teelab.api.teelab.productcatalog.domain.model.commands.UpdateProductPriceCommand;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductCommandService;
import quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public UUID handle(CreateProductCommand command) {
        var product = new Product(command);
        productRepository.save(product);
        return product.getId();
    }

    @Override
    public void handle(UpdateProductPriceCommand command) {
        Optional<Product> productOptional = productRepository.findById(command.productId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.updatePrice(command.price());
            productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + command.productId());
        }
    }
}
