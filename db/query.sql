-- 삭제 초기화
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Orders;
-- 외래 키 지원 활성
PRAGMA foreign_keys = ON;
-- 유저 테이블
CREATE TABLE Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password INTEGER NOT NULL
);
-- 유저 삽입
INSERT INTO Users (Username, Password)
VALUES ('user', 'pswd');
INSERT INTO Users (Username, Password)
VALUES ('tester', 'pswd');
-- 제품 테이블
CREATE TABLE Products (
    product_id INTEGER PRIMARY KEY AUTOINCREMENT,
    seller_id TEXT NOT NULL,
    name TEXT NOT NULL,
    price REAL NOT NULL,
    amount INT NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('Available', 'Reserved', 'SoldOut')),
    FOREIGN KEY (seller_id) REFERENCES Users(id)
);
-- 주문 테이블
CREATE TABLE Orders (
    buyer_id INT,
    product_id INT,
    price REAL NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('Reserved', 'Approval', 'Confirm')),
    PRIMARY KEY (buyer_id, product_id),
    FOREIGN KEY (buyer_id) REFERENCES Users(id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);
-- 제품 구매 수량에 따른 제품 상태 트리거
CREATE TRIGGER update_status_reserved
AFTER
UPDATE OF amount ON Products
    WHEN NEW.amount = 0 BEGIN
UPDATE Products
SET status = 'Reserved'
WHERE product_id = NEW.product_id;
END;
-- 주문 상태에 따른 제품 상태 트리거
CREATE TRIGGER update_status_soldout
AFTER
UPDATE OF status ON Orders
    WHEN NEW.status = 'Confirm' BEGIN
UPDATE Products
SET status = 'SoldOut'
WHERE Products.product_id == NEW.product_id
    AND NOT EXISTS (
        SELECT *
        FROM Orders
        WHERE Orders.product_id == NEW.product_id
            AND status != 'Confirm'
    );
END;