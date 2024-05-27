package com.es.productService.repository;

import com.es.productService.model.ProductItem;
import com.es.productService.model.ProductItemStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE ProductItem pi SET pi.productItemStatus = :newStatus WHERE pi.id IN :ids AND pi.productItemStatus = :currentStatus")
    int updateProductItemStatus(@Param("newStatus") ProductItemStatus newStatus, @Param("ids") List<UUID> ids, @Param("currentStatus") ProductItemStatus currentStatus);

    @Query("SELECT pi.id FROM ProductItem pi WHERE pi.product.id = :productId AND pi.productItemStatus = :status")
    List<UUID> findAllByProductIdAndStatus(@Param("productId") UUID productId, @Param("status") ProductItemStatus status);
}
