package com.clothing.InventoryService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "import_invoice")
public class ImportInvoice{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp updatedAt;
    @Column(name = "createdBy")
    private UUID createdBy;
    @JsonIgnore
    @OneToMany(mappedBy = "importInvoice")
    List<ImportItem> importItems = new ArrayList<>();;
}
