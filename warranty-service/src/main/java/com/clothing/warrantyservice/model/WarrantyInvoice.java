package com.clothing.warrantyservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "warranty_invoice")
public class WarrantyInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "warranty_id")
    private Warranty warranty;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "createdBy")
    private UUID createdBy;
}
