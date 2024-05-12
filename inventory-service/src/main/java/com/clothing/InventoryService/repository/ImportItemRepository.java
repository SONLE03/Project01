package com.clothing.InventoryService.repository;

import com.clothing.InventoryService.model.ImportItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportItemRepository extends JpaRepository<ImportItem, UUID> {

}
