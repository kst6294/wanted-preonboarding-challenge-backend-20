CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `membership` enum(''GUEST'',''NORMAL'') NOT NULL,
  `insert_date` datetime(6) NOT NULL,
  `update_date` datetime(6) NOT NULL,
  `insert_operator` varchar(255) NOT NULL,
  `update_operator` varchar(255) NOT NULL,
  `delete_yn` enum(''N'',''Y'') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK9q63snka3mdh91as4io72espi` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3


CREATE TABLE `product` (
    `product_id` bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `product_name` varchar(255) NOT NULL,
    `price` bigint NOT NULL,
    `quantity` int NOT NULL,
    `product_status` enum(''BOOKING'',''ON_STOCK'',''OUT_OF_STOCK'',''OUT_STOCK'') DEFAULT NULL,
    `insert_date` datetime(6) NOT NULL,
    `update_date` datetime(6) NOT NULL,
    `insert_operator` varchar(255) NOT NULL,
    `update_operator` varchar(255) NOT NULL,
    `delete_yn` enum(''N'',''Y'') NOT NULL,
    PRIMARY KEY (`product_id`),
    KEY `FK47nyv78b35eaufr6aa96vep6n` (`user_id`),
    CONSTRAINT `FK47nyv78b35eaufr6aa96vep6n` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

CREATE TABLE `orders` (
    `order_id` bigint NOT NULL AUTO_INCREMENT,
    `buyer_id` bigint NOT NULL,
    `seller_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `order_status` enum('COMPLETED','ORDERED','SETTLEMENT') NOT NULL,
    `insert_date` datetime(6) NOT NULL,
    `update_date` datetime(6) NOT NULL,
    `insert_operator` varchar(255) NOT NULL,
    `update_operator` varchar(255) NOT NULL,
    `delete_yn` enum('N','Y') NOT NULL,
    PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3


CREATE TABLE `orders_history` (
  `order_history_id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `order_status` enum(''COMPLETED'',''ORDERED'',''SETTLEMENT'') NOT NULL,
  `insert_date` datetime(6) NOT NULL,
  `update_date` datetime(6) NOT NULL,
  `insert_operator` varchar(255) NOT NULL,
  `update_operator` varchar(255) NOT NULL,
  `delete_yn` enum(''N'',''Y'') NOT NULL,
  PRIMARY KEY (`order_history_id`),
  KEY `FK6j8fy7l6bc3bskbgu028ng191` (`order_id`),
  CONSTRAINT `FK6j8fy7l6bc3bskbgu028ng191` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3


CREATE TABLE `orders_product_snapshot` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `price` bigint NOT NULL,
  `product_name` varchar(255) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKngqo5alcy00ll06engecg1vnj` (`product_id`),
  CONSTRAINT `FKngqo5alcy00ll06engecg1vnj` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3