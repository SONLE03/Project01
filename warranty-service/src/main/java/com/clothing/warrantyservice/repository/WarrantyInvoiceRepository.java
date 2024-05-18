package com.clothing.warrantyservice.repository;

import com.clothing.warrantyservice.model.WarrantyInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarrantyInvoiceRepository extends JpaRepository<WarrantyInvoice, UUID> {
}
