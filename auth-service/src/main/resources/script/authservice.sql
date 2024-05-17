CREATE TABLE IF NOT EXISTS `user_credential` (
  `id` BINARY(16) NOT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `role` TINYINT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `forgot_password` (
  `id` BIGINT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `expiry_date` DATETIME(6) DEFAULT NULL,
  `otp` INT DEFAULT NULL,
  `user_id` BINARY(16) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_user_id` (`user_id`),
  CONSTRAINT `FK_forgot_password_user_credential`
    FOREIGN KEY (`user_id`) REFERENCES `user_credential` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `forgot_password_seq` (
  `next_val` BIGINT DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `refresh_token` (
  `id` BINARY(16) NOT NULL,
  `expired` DATETIME(6) DEFAULT NULL,
  `refresh_token` VARCHAR(255) DEFAULT NULL,
  `user_id` BINARY(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_user_id` (`user_id`),
  CONSTRAINT `FK_refresh_token_user_credential`
    FOREIGN KEY (`user_id`) REFERENCES `user_credential` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;