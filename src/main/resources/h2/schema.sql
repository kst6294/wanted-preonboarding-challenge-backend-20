CREATE TABLE IF NOT EXISTS `member`
(
    `id`       INT  AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(255) NOT NULL,
    `email`    VARCHAR(255) NOT NULL,
    `password` CHAR(60)     NOT NULL
);

CREATE TABLE IF NOT EXISTS `product`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `seller_id` INT NOT NULL,
    `name`      VARCHAR(255) NOT NULL,
    `price`     INT NOT NULL,
    `state`     ENUM ('ON_SALE', 'RESERVED', 'COMPLETED') NOT NULL DEFAULT 'ON_SALE'
);

CREATE TABLE IF NOT EXISTS `contract`
(
    `id`        INT AUTO_INCREMENT PRIMARY KEY,
    `product_id` INT NOT NULL,
    `seller_id`  INT NOT NULL,
    `buyer_id`   INT NOT NULL,
    `state`      ENUM ('REQUESTED', 'ACCEPTED', 'REJECTED', 'CANCELED', 'COMPLETED') NOT NULL DEFAULT 'REQUESTED'
);

ALTER TABLE `member`
    ADD UNIQUE (`email`);
ALTER TABLE `product`
    ADD FOREIGN KEY (`seller_id`) REFERENCES `member` (`id`);
ALTER TABLE `contract`
    ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);
ALTER TABLE `contract`
    ADD FOREIGN KEY (`seller_id`) REFERENCES `member` (`id`);
ALTER TABLE `contract`
    ADD FOREIGN KEY (`buyer_id`) REFERENCES `member` (`id`);

INSERT INTO `member` (`username`, `email`, `password`) VALUES ('test', 'test@test.com', '$2a$10$q9O6f3gjpOO9hVxlzK1xTOe4OhXe1FI7jJmX02.op8mdWbdlhzGCK');

INSERT INTO `product` (`seller_id`, `name`, `price`, `state`) VALUES (1, 'test product', 1000, 'ON_SALE');
INSERT INTO `product` (`seller_id`, `name`, `price`, `state`) VALUES (1, 'test product 2', 2000, 'ON_SALE');