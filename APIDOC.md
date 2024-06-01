### 회원가입
`POST {{host}}/auth/signup`<br/>
Content-Type: application/json
```json
{
    "userId": "aaa",
    "name":"testName",
    "password":"bbb"
}
```

### 로그인
`POST {{host}}/login`
Content-Type: application/json
```json
{
    "userId": "admin",
    "password":"admin"
}
```

### 제품 조회
`GET {{host}}/api/products`

### 제품 생성
`POST {{host}}/api/products`<br/>
Content-Type: application/json
```json
{
    "productType": "UNCLASSIFIED",
    "productSellingStatus": "SELLING",
    "name": "productA",
    "description": "desc",
    "price": 1000,
    "sellingUserId": "admin"
}
```

### 제품 상세 조회
`GET {{host}}/api/products/{{productId}}`

### 주문 생성(주문을 생성하면 해당 제품을 예약하는 개념)
`POST {{host}}/api/orders` <br/>
Content-Type: application/json
```json
{
  "productNumber" : "4MGFY98WRS" // 시스템에서 생성된 랜덤값 기입
}
```

### 판매 승인(승인이 완료되면 거래완료)
`POST {{host}}/api/orders/approve/{{orderId}}`

### 내가 파는 제품 조회
`GET {{host}}/api/orders/my-buy-order?orderSearchRequest=SELLER`

### 내가 사는 제품 조회
`GET {{host}}/api/orders/my-buy-order?orderSearchRequest=BUYER`

### 구매 확정
`POST {{host}}/api/orders/complete/{{orderId}}`