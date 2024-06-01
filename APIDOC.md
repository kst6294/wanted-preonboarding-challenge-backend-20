### 회원가입
`POST {{host}}/auth/signup` <br/>
Content-Type: application/json <br/>
```json
{
    "userId": "aaa",
    "name":"testName",
    "password":"bbb"
}
```
response example <br/>
```json
{
  "code": 1,
  "message": "signup success",
  "timestamp": "2024-06-01T18:02:59.057604",
  "data": {
    "userId": "aaa",
    "name": "testName"
  }
}
```

### 로그인
`POST {{host}}/login` <br/>
Content-Type: application/json <br/>
```json
{
    "userId": "admin",
    "password":"admin"
}
```
response example <br/>
```json
{
  "code": 2,
  "message": "login success",
  "timestamp": "2024-06-01T18:01:57.6249591",
  "data": {
    "type": "Bearer ",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFXyBBRE1JTixST0xFXyBTRUxMRVIsUk9MRV9NRU1CRVIiLCJleHAiOjE3MTcyMzQzMTd9.DAfMNI_Lbs7gqSLkdfKNTu3_boYxbbjV3h_cFQ3aXoa8uFNRu_pSI3vq8rXOIPK226QWQUmfCy4FJDsDNVXZbQ",
    "accessTokenExpired": 1717234317589
  }
}
```

### 제품 조회
`GET {{host}}/api/products`<br/>

response example <br/>
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-06-01T18:04:20.7666271",
    "data": [
        {
            "id": 1,
            "productNumber": "INJ0579FGM",
            "productType": "UNCLASSIFIED",
            "productSellingStatus": "SELLING",
            "description": "desc",
            "name": "AAA",
            "price": 2000
        },
        {
            "id": 2,
            "productNumber": "KNCKGLEF18",
            "productType": "UNCLASSIFIED",
            "productSellingStatus": "SELLING",
            "description": "desc",
            "name": "AAA",
            "price": 3000
        }
    ]
}
```
### 제품 생성
`POST {{host}}/api/products`<br/>
Bearer Token : {{JWT TOKEN}} <br/>
Content-Type: application/json <br/>
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
response example
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-06-01T18:07:08.5661637",
    "data": {
        "id": 5,
        "productNumber": "8I4EU8599J",
        "productType": "UNCLASSIFIED",
        "productSellingStatus": "SELLING",
        "description": "desc",
        "name": "testname",
        "price": 1000
    }
}
```

### 제품 상세 조회
`GET {{host}}/api/products/{{productId}}` <br/>

response example
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-06-01T18:11:03.2201763",
    "data": {
        "id": 1,
        "productNumber": "INJ0579FGM",
        "productType": "UNCLASSIFIED",
        "productSellingStatus": "SELLING",
        "description": "desc",
        "name": "AAA",
        "price": 2000
    }
}
```


### 주문 생성(주문을 생성하면 해당 제품을 예약하는 개념)
`POST {{host}}/api/orders` <br/>
Bearer Token : {{JWT TOKEN}} <br/>
Content-Type: application/json <br/>
```json
{
  "productNumber" : "4MGFY98WRS" // 시스템에서 생성된 랜덤값 기입
}
```

### 판매 승인(승인이 완료되면 거래완료)
`POST {{host}}/api/orders/approve/{{orderId}}` <br/>
Bearer Token : {{JWT TOKEN}} <br/>
response example
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-05-31T18:29:52.476715",
    "data": {
        "id": 1,
        "totalPrice": 1000,
        "orderStatus": "APPROVED",
        "orderedAt": "2024-05-31T13:22:18.450841",
        "product": {
            "id": 1,
            "productNumber": "4MGFY98WRS",
            "productType": "UNCLASSIFIED",
            "productSellingStatus": "RESERVED",
            "description": "desc",
            "name": "productA",
            "price": 1000
        }
    }
}
```

### 내가 사는 제품 조회
`GET {{host}}/api/orders/my-buy-order?orderSearchRequest=BUYER` <br/>
Bearer Token : {{JWT TOKEN}} <br/>
response example
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-05-31T14:27:36.219136",
    "data": [
        {
            "id": 1,
            "totalPrice": 1000,
            "orderStatus": "INIT",
            "orderedAt": "2024-05-31T13:22:18.450841",
            "product": {
                "id": 1,
                "productNumber": "4MGFY98WRS",
                "productType": "UNCLASSIFIED",
                "productSellingStatus": "SELLING",
                "description": "desc",
                "name": "productA",
                "price": 1000
            }
        }
    ]
}
```
### 내가 파는 제품 조회
`GET {{host}}/api/orders/my-buy-order?orderSearchRequest=SELLER` <br/>
Bearer Token : {{JWT TOKEN}} <br/>
*내가 사는 제품과 응답 명세 동일*

### 구매 확정
`POST {{host}}/api/orders/complete/{{orderId}}` <br/>
Bearer Token : {{JWT TOKEN}} <br/>
response example
```json
{
    "code": 0,
    "message": "success",
    "timestamp": "2024-05-31T18:44:28.750698",
    "data": {
        "id": 5,
        "totalPrice": 1000,
        "orderStatus": "COMPLETED",
        "orderedAt": "2024-05-31T18:26:53.198105",
        "product": {
            "id": 1,
            "productNumber": "4MGFY98WRS",
            "productType": "UNCLASSIFIED",
            "productSellingStatus": "SOLD_OUT",
            "description": "desc",
            "name": "productA",
            "price": 1000
        }
    }
}
```