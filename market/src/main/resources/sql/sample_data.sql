INSERT INTO member (member_number, member_id, name, password, role, status, register_date, update_date) VALUES
(0, 'user', 'user', '$2a$10$M3xGTI6Grz9QL0J42ehyMeYMz0t8kGfUVuSVTiSCBW.hpIt9y/RVS', 'MEMBER', 'ACTIVE', now(), now());

INSERT INTO product (quantity, price, product_number, register_date, seller_number, update_date, description, name, status) VALUES
(1, 10000, 0, now(), 0, now(), 'sample', 'sample product', 'ON_SALE')
