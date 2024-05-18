package com.es.productService.repository;

import com.es.productService.dto.response.ProductResponse;
import com.es.productService.dto.response.ProductToOrder;
import com.es.productService.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
//    @Query("SELECT p FROM ProductEntity p WHERE p.productStatus <> 3")
//    List<ProductEntity> getAllProducts();

    @Query("SELECT new com.es.productService.dto.response.ProductResponse(p.id, p.product_Name, p.description, p.price, p.quantity, p.warrantyPeriod, p.productStatus) FROM ProductEntity p WHERE p.productStatus != 'DELETED'")
    List<ProductResponse> findAllProducts();

    @Query("SELECT new com.es.productService.dto.response.ProductResponse(p.id, p.product_Name, p.description, p.price, p.quantity, p.warrantyPeriod , p.productStatus) FROM ProductEntity p WHERE p.id = :productId AND p.productStatus != 'DELETED'")
    ProductResponse findProductById(UUID productId);

    @Query("SELECT new com.es.productService.dto.response.ProductToOrder(p.id, p.product_Name, p.price, p.quantity, p.warrantyPeriod) FROM ProductEntity p WHERE p.id = :productId")
    ProductToOrder getProductToOrder(UUID productId);
}
