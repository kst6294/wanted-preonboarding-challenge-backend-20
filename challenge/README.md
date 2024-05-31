# API 명세

> *기울임체*가 선언되지 않은 모든 api는 json 형태입니다.

## 회원

### 회원가입

#### [POST] /signup

> loginId: 로그인 아이디
>
> password: 비밀번호

### 로그인

#### [POST] /login

> *x-www-form-urlencoded*
>
> loginId: 로그인 아이디
>
> password: 비밀번호

### 로그아웃

#### [POST] /logout

>

## 제품

### 제품 등록

#### [POST] /products

> name: 제품명
>
> price: 제품 가격

### 제품 구매

#### [POST] /products/purchase

> productId: 제품 번호

### 제품 목록 조회

#### GET /products?page={page}&size={size}

> page: 페이지 번호, optional
>
> size: 한 페이지의 데이터 개수, optional

### 제품 상세 조회

#### GET /products/{productId}

조회자가 구매자인지, 판매자인지에 따라 응답이 달라질 수 있습니다.

> productId: 제품 번호

### 구매 제품 조회

#### GET /products/purchase?page={page}&size={size}

> page: 페이지 번호, optional
>
> size: 한 페이지의 데이터 개수, optional

### 판매자 예약 제품 조회

#### GET /products/reserve?page={page}&size={size}

> page: 페이지 번호, optional
>
> size: 한 페이지의 데이터 개수, optional

## 거래 내역

### 판매 승인

#### POST /transacts/approve

> productId: 제품 번호
>
> buyerId: 구매자 번호
