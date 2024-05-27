package com.clothing.warrantyservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "warranty")
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "productId", nullable = false)
    private UUID productItemId;
    @Column(name = "customerId", nullable = false)
    private UUID customerId;
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WarrantyStatus warrantyStatus;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "createdBy")
    private UUID createdBy;
}
