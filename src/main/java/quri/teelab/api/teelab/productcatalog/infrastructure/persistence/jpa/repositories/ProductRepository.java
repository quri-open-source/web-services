package quri.teelab.api.teelab.productcatalog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quri.teelab.api.teelab.productcatalog.domain.model.aggregates.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Finds all products by projectId string value.
     * Part of the anticorruption layer to allow external systems to use string IDs
     * while maintaining domain model integrity.
     *
     * @param projectId the project ID as a string
     * @return list of matching products
     */
    @Query("SELECT p FROM Product p WHERE p.projectId.value = :projectId")
    List<Product> findByProjectId(@Param("projectId") String projectId);
}
