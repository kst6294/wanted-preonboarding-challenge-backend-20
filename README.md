# Wanted Market API

> 원티드 프리온보딩 챌린지 백엔드 20 사전과제

## 🛠️ 기술 스택

[![Node.js](https://img.shields.io/badge/Node.js-%235FA04E?style=flat&logo=nodedotjs&logoColor=white)](https://nodejs.org)
[![Express](https://img.shields.io/badge/Express-%23000000?style=flat&logo=express)](https://expressjs.com/ko/)
[![Knex.js](https://img.shields.io/badge/Knex.js-%23D26B38?style=flat&logo=knexdotjs&logoColor=white)](https://knexjs.org)
[![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens)](https://jwt.io)
[![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat&logo=sqlite)](https://www.sqlite.org)
[![Jest](https://img.shields.io/badge/Jest-C21325?style=flat&logo=jest&logoColor=white)](https://jestjs.io)

## 📄 API 명세서

[![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white)](https://documenter.getpostman.com/view/34090317/2sA3QqhYdL)

| Category | Summary            | Method | URI                      | Request Header       | Request Body                                    | Success Code |
| -------- | ------------------ | ------ | ------------------------ | -------------------- | ----------------------------------------------- | ------------ |
| 계정     | 토큰 발급          | POST   | /token/                  |                      | username: string <br> password: string          | 200          |
| 비회원   | 목록 조회          | GET    | /products/               |                      |                                                 | 200          |
|          | 상세 조회          | GET    | /products/{product_id}   |                      |                                                 | 200          |
| 회원     | 제품 등록          | POST   | /products/register       | Authorization: token | name: string <br> price: float <br> amount: int | 201          |
|          | 제품 구매          | POST   | /products/purchase       | Authorization: token | product_id: int                                 | 201          |
|          | 제품 주문서 보기   | GET    | /orders/                 | Authorization: token | product_id: int                                 | 200          |
|          | 판매 승인          | POST   | /orders/sales-approval   | Authorization: token | product_id: int <br> buyer_id: int              | 201          |
|          | 구매 확정          | POST   | /orders/purchase-confirm | Authorization: token | product_id: int                                 | 201          |
|          | 구매한 용품 조회   | GET    | /user/purchased-list     | Authorization: token |                                                 | 200          |
|          | 예약중인 용품 조회 | GET    | /user/reserved-list      | Authorization: token |                                                 | 200          |

## 📊 테스트 리포터

[![Jest Reporter](https://img.shields.io/badge/Jest_Reporter-C21325?style=flat&logo=jest&logoColor=white)](https://narcisource.github.io/wanted-preonboarding-challenge-backend-20/)

## 🏗️ ERD

![ERD](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/assets/26417221/eabf257d-23b5-48eb-95ed-498d218bafc8)

## 📂 프로젝트 구조

```
wanted-preonboarding-challenge-backend-20
├─ .git
├─ .gitignore
├─ app.js
├─ bin
│  └─ www
├─ db
│  ├─ dbClient.js
│  ├─ knexfile.js
│  ├─ query.sql
│  └─ test.sqlite3
├─ gh-pages
│  ├─ index.html
│  └─ jest-html-reporters-attach
│     └─ index
│        ├─ index.js
│        └─ result.js
├─ middlewares
│  ├─ auth.js
│  └─ dbErrorHandler.js
├─ model
│  ├─ basicDao.js
│  ├─ orderDao.js
│  ├─ productDao.js
│  └─ userDao.js
├─ package-lock.json
├─ package.json
├─ README.md
├─ routes
│  ├─ products.js
│  ├─ token.js
│  └─ user.js
├─ services
│  ├─ productService.js
│  └─ userService.js
└─ __tests__
   └─ app.test.js
```

## 🚀 실행 방법

1. 요구 패키지 설치  
   `npm install`

2. 환경변수 .env

    ```python
    PORT=5002
    NODE_ENV=development
    JWT_SECRET= #openssl rand -hex 64
    ```

3. db 생성

    - 로컬용 `npm setup-dev-db`
    - 테스트용 `npm setup-test-db`

4. 서버 실행  
   `npm run start`

5. 테스트 실행  
   `npm run test`

<br>
<br>
<br>
<br>
<br>

## 원티드 프리온보딩 챌린지 백엔드 20 사전과제

### 설명

사용자간 거래가 가능한 Wanted Market API를 생성해야합니다. 요구사항에 맞춰 진행해주세요.
요구사항은 **공통**과 **1단계(필수)**, **2단계(선택)** 로 나누어져 있습니다.

공통과 1단계는 필수로 진행해주시고, 2단계는 1단계를 마무리한 이후에 순차적으로 진행하시는 것을 추천합니다.
스프린트를 진행하면서 기능이 어떻게 발전해나가는지 사전 과제를 통해서 체험해보시면 좋겠습니다.

<br>

### 요구사항

##### 1단계 (필수)

1. 제품 등록과 구매는 회원만 가능합니다.

    - [x] jwt 토큰을 발급받은 회원이 요청시 토큰을 보내면 인증한다. [auth.js#L3-L12](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/middlewares/auth.js#L3-L12)

2. 비회원은 등록된 제품의 목록조회와 상세조회만 가능합니다.

    - [x] `GET	/products/` [products.js#L10-L18](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/products.js#L10-L18)
    - [x] `GET	/products/{product_id}` [products.js#L22-L32](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/products.js#L22-L32)

3. 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어야하고, 목록조회와 상세조회시에 예약상태를 포함해야합니다.

    - [x] Products 테이블 생성 [query.sql#L21-L29](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L21-L29)

4. 제품의 상태는 "판매중", "예약중", "완료" 세가지가 존재합니다.

    - [x] enum 형태(sqlite에서 IN)로 구현 [query.sql#L27](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L27)

5. 구매자가 제품의 상세페이지에서 구매하기 버튼을 누르면 거래가 시작됩니다.

    - [x] `POST /products/purchase` [products.js#L51-L62](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/products.js#L51-L62)

6. 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다.

    - [x] `GET	/orders/` [orders.js#L11-L23](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/orders.js#L11-L23)

7. 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있습니다.

    - [x] `GET	/user/purchased-list` [users.js#L10-L20](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/users.js#L10-L20)
    - [x] `GET	/user/reserved-list` [users.js#L24-L35](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/users.js#L24-L35)

8. 판매자는 거래진행중인 구매자에 대해 '판매승인'을 하는 경우 거래가 완료됩니다.

    - [x] `POST /orders/sales-approval` [orders.js#L28-L39](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/orders.js#L28-L39)

<br>

##### 2단계 (선택)

9. 제품에 수량이 추가됩니다. 제품정보에 "제품명", "가격", "예약상태", "수량"이 포함되어야합니다.

    - [x] [query.sql#L26](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L26), [productService.js#L42](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/services/productService.js#L42)

10. 다수의 구매자가 한 제품에 대해 구매하기가 가능합니다. (단, 한 명이 구매할 수 있는 수량은 1개뿐입니다.)

    - [x] Orders 관계 테이블 생성 [query.sql#L31-L39](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L31-L39)

11. 구매확정의 단계가 추가됩니다. 구매자는 판매자가 판매승인한 제품에 대해 구매확정을 할 수 있습니다.

    - [x] `POST /orders/purchase-confirm` [orders.js#L44-L55](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/routes/orders.js#L44-L55)

12. 거래가 시작되는 경우 수량에 따라 제품의 상태가 변경됩니다.

    - 추가 판매가 가능한 수량이 남아있는 경우 - 판매중
    - 추가 판매가 불가능하고 현재 구매확정을 대기하고 있는 경우 - 예약중
    - 모든 수량에 대해 모든 구매자가 모두 구매확정한 경우 - 완료

    - [x] sql 트리거로 수행 [query.sql#L40-L63](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L40-L63)

13. "구매한 용품"과 "예약중인 용품" 목록의 정보에서 구매하기 당시의 가격 정보가 나타나야합니다.

    - 예) 구매자 A가 구매하기 요청한 당시의 제품 B의 가격이 3000원이었고 이후에 4000원으로 바뀌었다 하더라도 목록에서는 3000원으로 나타나야합니다.

    - [x] Orders 테이블에 price 컬럼 [query.sql#L34](https://github.com/NarciSource/wanted-preonboarding-challenge-backend-20/blob/e5dcf993274d3f308dc35f11838ccc6213e46786/db/query.sql#L34)

##### 공통

0. Python이나 Java 기반의 프레임워크를 사용하시길 권장합니다.
1. 구매취소는 고려하지 않습니다.
2. 요구사항에 모호한 부분이 많은게 맞습니다. 같은 요구사항에 대해 다양한 시각을 보여주세요.
3. 검증이 필요한 부분에 대해 테스트코드를 작성해주세요.
4. 작성한 API에 대한 명세를 작성해주세요.
5. 개발과정에서 어려웠던 부분이나 예기치 못한 케이스가 있었다면 기록을 남겨주세요.
6. 다른분들의 PR을 보면서 리뷰를 해보세요. 궁금한점을 자유롭게 남기면서 서로의 의견을 주고 받아주세요!
7. 요구사항을 잘 진행해주신 분들 중에서 추첨하여 선물을 드리겠습니다 :)

<br>

### 제출방법

1. 이 repository 를 fork 해주세요.
2. feature/{name} 으로 브랜치를 생성해주세요. 예: feature/suntae-kim
3. 과제를 진행해주세요.
4. 소스코드를 Push 하고 PR을 올려주세요.
5. 요구사항에 대해서 궁금한 점이나 이해가 안되는 부분이 있다면 이슈를 남겨주시거나, 편하게 연락주세요 - kst6294@gmail.com
