## 원티드 프리온보딩 챌린지 백엔드 20 사전과제  

### API 명세
| 기능             | HTTP Method | API Endpoint           | 분류    |
|----------------|-------------|-----------------------|--------|
| 회원가입        | POST        | /auth/register        | USER   |
| 로그인          | POST        | /auth/login           | USER   |
| 제품 등록       | POST        | /product/register     | PRODUCT|
| 제품 목록 조회  | GET         | /product/list         | PRODUCT|
| 제품 상세 조회  | GET         | /product/{productId}  | PRODUCT|
| 구매            | POST        | /order/purchase/{productId} | ORDER |
| 판매 승인       | PATCH       | /order/approval/{orderId} | ORDER |
| 사용자 거래 목록 조회 | GET   | /order/my-transactions | ORDER |  


요구사항 1단계까지 완료했습니다.
