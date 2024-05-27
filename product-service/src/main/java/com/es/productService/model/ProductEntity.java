package com.es.productService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "product_name", nullable = false)
    private String product_Name;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "category_id", nullable = false)
    private Integer category;
    @Column(name = "warrantyPeriod", nullable = false)
    private Integer warrantyPeriod;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp updatedAt;
    @Column(name = "createdBy")
    private UUID createdBy;
}
