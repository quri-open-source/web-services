package quri.teelab.api.teelab.productcatalog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetAllProductsQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductByIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductsByProjectIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.services.ProductQueryService;
import quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }

    @Override
    public List<Product> handle(GetProductsByProjectIdQuery query) {
        return productRepository.findByProjectId(query.projectId().value());
    }
}
