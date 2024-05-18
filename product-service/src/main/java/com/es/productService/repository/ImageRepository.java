package com.es.productService.repository;

import com.es.productService.dto.response.ImageResponse;
import com.es.productService.model.Image;
import com.es.productService.model.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.product = :product")
    void deleteByProduct(ProductEntity product);

    @Query("SELECT i FROM Image i WHERE i.product = :product")
    List<Image> getImageByProduct(ProductEntity product);
    @Query("SELECT NEW com.es.productService.dto.response.ImageResponse(i.id, i.url) FROM Image i WHERE i.product.id = :productId")
    List<ImageResponse> findImagesByProductId(UUID productId);

}