## 원티드 프리온보딩 챌린지 백엔드 20 사전과제

### ERD
![PreOnBoardingERD](https://github.com/BeefCutlet/wanted-preonboarding-challenge-backend-20/assets/77325024/48af9be9-224e-4ad1-b8c3-75396e4b35f9)



### API

| Domain | HTTP Method | URL                        | Detail                           |
|:------:|:-----------:|:---------------------------|----------------------------------|
|  Auth  |    POST     | /auth/login                | 로그인                              |
| Member |    POST     | /members/sign-up           | 회원가입                             |
|  Item  |    POST     | /items                     | 제품 등록                            |
|        |     GET     | /items/list                | 제품 목록 조회                         |
|        |     GET     | /items/{itemId}            | 제품 단건 조회                         |
| Order  |    POST     | /orders                    | 주문 등록                            |
|        |     PUT     | /orders/approval           | 주문 상태 변경 - 판매승인                  |
|        |     PUT     | /orders/completion         | 주문 상태 변경 - 구매확정                  |
|        |     GET     | /orders/history/{itemId}   | 제품에 대한 판매자와의 주문 내역 조회            |
|        |     GET     | /orders/completion         | 구매자가 구매 확정한 주문 내역 조회             |
|        |     GET     | /orders/approval           | 구매자가 구매 요청한 주문 중 판매 승인된 주문 내역 조회 |
|        |     GET     | /orders/reservation        | 구매자가 구매 예약한 주문 조회                |
|        |     GET     | /orders/seller/reservation | 판매자가 구매 요청 받은 주문 목록 조회           |

### API 상세
https://hsa-1.gitbook.io/wantedpobackend/