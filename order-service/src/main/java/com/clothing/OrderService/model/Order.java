package com.clothing.OrderService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private UUID customer;
    @Column(name = "paymentMethod")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "note")
    private String note;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "payment_at")
    private Date paymentAt;
    @Column(name = "canceled_at")
    private Date canceledAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp updatedAt;
    @Column(name = "createdBy")
    private UUID createdBy;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    Set<OrderItem> orderItems;
}
