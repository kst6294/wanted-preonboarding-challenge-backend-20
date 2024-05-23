CREATE TABLE IF NOT EXISTS `Member`
(
    `id`       INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    `password` CHAR(60)     NOT NULL
);

CREATE TABLE IF NOT EXISTS `Product`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `seller_id` INT NOT NULL,
    `name`      VARCHAR(255) NOT NULL,
    `price`     DECIMAL(10, 2) NOT NULL,
    `state`     ENUM ('ON_SALE', 'RESERVED', 'COMPLETED') NOT NULL DEFAULT 'ON_SALE'
);

CREATE TABLE IF NOT EXISTS `Contract`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `seller_id`  INT NOT NULL,
    `buyer_id`   INT NOT NULL,
    `price`      DECIMAL(10, 2) NOT NULL,
    `state`      ENUM ('REQUESTED', 'ACCEPTED', 'REJECTED', 'CANCELED', 'COMPLETED') NOT NULL DEFAULT 'REQUESTED'
);

ALTER TABLE `Member`
    ADD UNIQUE (`email`);
ALTER TABLE `Product`
    ADD FOREIGN KEY (`seller_id`) REFERENCES `Member` (`id`);
ALTER TABLE `Contract`
    ADD FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`);
ALTER TABLE `Contract`
    ADD FOREIGN KEY (`seller_id`) REFERENCES `Member` (`id`);
ALTER TABLE `Contract`
    ADD FOREIGN KEY (`buyer_id`) REFERENCES `Member` (`id`);

