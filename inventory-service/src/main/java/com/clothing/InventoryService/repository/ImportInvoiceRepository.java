package com.clothing.InventoryService.repository;

import com.clothing.InventoryService.model.ImportInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportInvoiceRepository extends JpaRepository<ImportInvoice, UUID> {

}
