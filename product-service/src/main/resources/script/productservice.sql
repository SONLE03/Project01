CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
AUTO_INCREMENT=4
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `product` (
  `id` BINARY(16) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `image` VARCHAR(255) DEFAULT NULL,
  `price` DECIMAL(38,2) NOT NULL,
  `status` INT DEFAULT NULL,
  `product_name` VARCHAR(255) NOT NULL,
  `quantity` INT NOT NULL,
  `category_id` INT DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_category_id` (`category_id`),
  CONSTRAINT `FK_category_id`
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;