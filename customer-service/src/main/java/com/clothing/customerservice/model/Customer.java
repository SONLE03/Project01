package com.clothing.customerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "ward")
    private String ward;
    @Column(name = "specific_address")
    private String specificAddress;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default NOW()")
    @JsonFormat(timezone = "GMT+7")
    private Timestamp updatedAt;
}
