package quri.teelab.api.teelab.productcatalog.domain.services;

import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetAllProductsQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductByIdQuery;
import quri.teelab.api.teelab.productcatalog.domain.model.queries.GetProductsByProjectIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    List<Product> handle(GetAllProductsQuery query);

    Optional<Product> handle(GetProductByIdQuery query);

    List<Product> handle(GetProductsByProjectIdQuery query);
}
