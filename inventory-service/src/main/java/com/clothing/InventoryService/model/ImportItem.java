package com.clothing.InventoryService.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "import_item")
public class ImportItem {
    @EmbeddedId
    private ImportItemId id;
    @ManyToOne
    @MapsId("importId")
    @JoinColumn(name = "import_id")
    private ImportInvoice importInvoice;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "total")
    private BigDecimal total;

}