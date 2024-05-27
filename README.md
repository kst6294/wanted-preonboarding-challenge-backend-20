## 원티드 프리온보딩 챌린지 백엔드 20 사전과제

### ERD
![PreOnBoardingERD](https://github.com/BeefCutlet/wanted-preonboarding-challenge-backend-20/assets/77325024/01ef358f-99ea-498b-aeae-38cb25c2138b)



### API

| Domain | HTTP Method | URL                        | Detail                 |
|:------:|:-----------:|:---------------------------|------------------------|
|  Auth  |    POST     | /auth/login                | 로그인                    |
| Member |    POST     | /members/sign-up           | 회원가입                   |
|  Item  |    POST     | /items                     | 제품 등록                  |
|        |     GET     | /items/list                | 제품 목록 조회               |
|        |     GET     | /items/{itemId}            | 제품 단건 조회               |
| Order  |    POST     | /orders                    | 주문 등록                  |
|        |     PUT     | /orders/approval           | 주문 상태 변경 - 판매승인        |
|        |     PUT     | /orders/completion         | 주문 상태 변경 - 구매확정        |
|        |     GET     | /orders/history/{itemId}   | 제품에 대한 판매자와의 주문 내역 조회  |
|        |     GET     | /orders/completion         | 구매자가 구매 확정한 주문 내역 조회   |
|        |     GET     | /orders/reservation        | 구매자가 구매 예약한 주문 조회      |
|        |     GET     | /orders/seller/reservation | 판매자가 구매 요청 받은 주문 목록 조회 |

### API 상세
<details>
<summary>POST /auth/login</summary>

Request
> -H "Content-Type: application/json" \
-d '{ "email": "email@email.com", "password": "password" }'

Response
> 200 Ok
</details>

<details>
<summary>POST /members/sign-up</summary>

Request
> -H "Content-Type: application/json" \
-d '{ \
&emsp;&emsp;&emsp;"email": "email@email.com", \
&emsp;&emsp;&emsp;"password": "password", \
&emsp;&emsp;&emsp;"name": "MemberName", \
&emsp;&emsp;&emsp;"nickname": "MemberNickname", \
&emsp;&nbsp;}'

Response
> 200 Ok
</details>

<details>
<summary>POST /items</summary>

Request
> -H "Content-Type: application/json" \
-d '{ "name": "ItemName", "price": "10000" }'

Response
> 200 Ok
</details>

<details>
<summary>GET /items/list</summary>

Request
> -H "Content-Type: application/json" \

Response 
> 200 Ok \
{ \
&emsp;&emsp; "items": [ \
&emsp;&emsp;&emsp;&emsp; { "price": "10000", "status": "FOR_SALE" }, \
&emsp;&emsp;&emsp;&emsp; { "price": "20000", "status": "RESERVED" }, \
&emsp;&emsp;&emsp;&emsp; { "price": "30000", "status": "COMPLETE" }, \
&emsp;&emsp; ] \
}
</details>

<details>
<summary>GET /items/{itemId}</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \

Response
> 200 Ok \
{ \
&emsp;&emsp; "price": "10000", \
&emsp;&emsp; "status": "FOR_SALE", \
&emsp;&emsp; "order": { \
&emsp;&emsp;&emsp;&emsp; "itemName": "ItemName" \
&emsp;&emsp;&emsp;&emsp; "totalPrice": "30000" \
&emsp;&emsp;&emsp;&emsp; "createdAt": "2024-05-27T13:35:26.843093" \
&emsp;&emsp;
}
</details>

<details>
<summary>POST /orders</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \
-d '{ "itemId": "1", "price": "10000" }'

Response
> 200 Ok
</details>

<details>
<summary>PUT /orders/approval</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \
-d '{ "itemId": "1", "orderId": "1" }'

Response
> 200 Ok
</details>

<details>
<summary>PUT /orders/completion</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \
-d '{ "itemId": "1", "orderId": "1" }'

Response
> 200 Ok
</details>

<details>
<summary>GET /orders/history/{itemId}</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \

Response
> 200 Ok \
{ \
&emsp;&emsp; "price": "10000", \
&emsp;&emsp; "status": "FOR_SALE", \
&emsp;&emsp; "order": { \
&emsp;&emsp;&emsp;&emsp; "itemName": "ItemName" \
&emsp;&emsp;&emsp;&emsp; "totalPrice": "30000" \
&emsp;&emsp;&emsp;&emsp; "createdAt": "2024-05-27T13:35:26.843093" \
&emsp;&emsp;
}
</details>

<details>
<summary>GET /orders/completion</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \

Response
> 200 Ok \
{ \
&emsp;&emsp; "price": "10000", \
&emsp;&emsp; "status": "FOR_SALE", \
&emsp;&emsp; "order": { \
&emsp;&emsp;&emsp;&emsp; "itemName": "ItemName" \
&emsp;&emsp;&emsp;&emsp; "totalPrice": "30000" \
&emsp;&emsp;&emsp;&emsp; "createdAt": "2024-05-27T13:35:26.843093" \
&emsp;&emsp;
}
</details>

<details>
<summary>GET /orders/reservation</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \

Response
> 200 Ok \
{ \
&emsp;&emsp; "price": "10000", \
&emsp;&emsp; "status": "FOR_SALE", \
&emsp;&emsp; "order": { \
&emsp;&emsp;&emsp;&emsp; "itemName": "ItemName" \
&emsp;&emsp;&emsp;&emsp; "totalPrice": "30000" \
&emsp;&emsp;&emsp;&emsp; "createdAt": "2024-05-27T13:35:26.843093" \
&emsp;&emsp;
}
</details>

<details>
<summary>GET /orders/seller/reservation</summary>

Request
> -H "Content-Type: application/json" \
-H "Set-Cookie: { "JSESSIONID" : "SessionID" }" \

Response
> 200 Ok \
{ \
&emsp;&emsp; "price": "10000", \
&emsp;&emsp; "status": "FOR_SALE", \
&emsp;&emsp; "order": { \
&emsp;&emsp;&emsp;&emsp; "itemName": "ItemName" \
&emsp;&emsp;&emsp;&emsp; "totalPrice": "30000" \
&emsp;&emsp;&emsp;&emsp; "createdAt": "2024-05-27T13:35:26.843093" \
&emsp;&emsp;
}
</details>
