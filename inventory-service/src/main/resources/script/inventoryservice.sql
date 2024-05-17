CREATE TABLE IF NOT EXISTS `import_invoice` (
  `id` BINARY(16) NOT NULL,
  `total` DECIMAL(38,2) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `import_item` (
  `product_item_id` BINARY(16) NOT NULL,
  `price` DECIMAL(38,2) DEFAULT NULL,
  `quantity` INT DEFAULT NULL,
  `total` DECIMAL(38,2) DEFAULT NULL,
  `import_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`import_id`, `product_item_id`),
  CONSTRAINT `FK_import_item_import_invoice`
    FOREIGN KEY (`import_id`) REFERENCES `import_invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
