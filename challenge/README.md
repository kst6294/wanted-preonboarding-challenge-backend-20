# API 명세

> *기울임체*가 선언되지 않은 모든 api는 json 형태입니다.

## 회원

### 로그인

#### /signup

> **POST**
>
> loginId: 로그인 아이디
>
> password: 비밀번호

#### /login

> **POST**
>
> *x-www-form-urlencoded*
>
> loginId: 로그인 아이디
>
> password: 비밀번호

#### /logout

> **POST**

## 제품

### 제품 등록

#### /products/register

> **POST**
>
> name: 제품명
>
> price: 제품 가격
