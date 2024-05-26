-- 삭제 초기화
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Users;

-- 제품 테이블
CREATE TABLE Products (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT NOT NULL,
    Price REAL NOT NULL,
    Seller TEXT NOT NULL,
    Buyer TEXT,
    ReservationState TEXT NOT NULL CHECK (ReservationState IN ('Available', 'Reserved', 'SoldOut'))
);

-- 유저 테이블
CREATE TABLE Users (
    Username TEXT NOT NULL,
    Password INTEGER NOT NULL
);

-- 유저 삽입
INSERT INTO Users (Username, Password) VALUES ('user', 'pswd');
INSERT INTO Users (Username, Password) VALUES ('tester', 'pswd');