insert into "member" ("created_at", "password", "updated_at", "user_id", "member_id") values ('2024-05-22T15:25:01.810', '12345678a', '2024-05-22T15:25:01.810', 'seonwoo_jung1', default);
insert into "member" ("created_at", "password", "updated_at", "user_id", "member_id") values ('2024-05-22T15:25:01.810', '12345678a', '2024-05-22T15:25:01.810', 'seonwoo_jung2', default);
insert into "member" ("created_at", "password", "updated_at", "user_id", "member_id") values ('2024-05-22T15:25:01.810', '12345678a', '2024-05-22T15:25:01.810', 'seonwoo_jung3', default);

insert into "product" ("created_at", "name", "price", "quantity", "seller_id", "status", "updated_at", "product_id") values ('2024-05-22T15:25:01.857', '치킨', 20000, 1, 1, 'SALE', '2024-05-22T15:25:01.857', default);
insert into "product" ("created_at", "name", "price", "quantity", "seller_id", "status", "updated_at", "product_id") values ('2024-05-22T15:25:01.857', '피자', 15000, 1, 2, 'SALE', '2024-05-22T15:25:01.857', default);
insert into "product" ("created_at", "name", "price", "quantity", "seller_id", "status", "updated_at", "product_id") values ('2024-05-22T15:25:01.857', '스파게티', 13000, 1, 3, 'SALE', '2024-05-22T15:25:01.857', default);

insert into "order" ("buyer_id", "created_at", "order_status", "seller_id", "updated_at", "order_id") values (1, '2024-05-22T16:23:52.290', 'NONE', 1, '2024-05-22T16:23:52.290', default);
insert into "order_item" ("created_at", "order_id", "price", "product_id", "product_name", "quantity", "updated_at", "order_item_id") values ('2024-05-22T16:23:52.284', 1, 20000, 1, '치킨', 1, '2024-05-22T16:23:52.284', default);

update "product" set "quantity" = 0, "status" = 'RESERVATION', "updated_at" = '2024-05-22T16:23:52.357' where "product_id" = 1