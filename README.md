## 원티드 프리온보딩 챌린지 백엔드 20 사전과제

## 시나리오 
### 1. 회원 가입

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/6118d594-9a3d-47ea-a818-33402b1840ee)


## 


#### <member 테이블>

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/f9d22bb4-43f9-4e1e-806c-3d86867d130e)
#### - test1 ~ test5 까지의 회원 생성


<hr>

### 2. 로그인

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/a7e6443c-92e7-4fd0-836e-b0a48a6aa44c)

<hr>

### 3. 아이템 생성

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/c1f34e19-d9cd-4727-992f-4b70c6234579)

<hr>

#### <item 테이블>
![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/d2b6e44d-7131-4c22-8181-16487b5e190d)

## 

### 4. 아이템 구매

#### 4-1. 다른 계정으로 로그인

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/d81cabeb-4888-452f-80c5-8efdfb83550a)

#### 4-2 해당 아이템 예약 신청 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/48bf1211-f4fd-4c96-9673-f2503ea1246c)

#### <buy 테이블>

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/c783a7fd-dfa0-425f-aaad-c21e11f8feb8)

#### 4-3 판매자 계정으로 로그인

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/88263bcb-ff0b-449d-b092-d54932f61cff)

#### 4-4  판매 완료

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/88395cba-d119-4041-a866-b282ec46345b)


![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/ece930df-d976-4f5e-bcf2-ca00553276b8)


#### 4-5. 4-1의 계정으로 로그인 이후 구매 확정 


![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/1244fd2e-d7e5-4e57-8493-a53ffab6b19f)


![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/f46585bd-0972-4244-8813-570ff12c5de5)




### 요구사항

##### 1단계 (필수)
1. 제품 등록과 구매는 회원만 가능합니다. 
2. 비회원은 등록된 제품의 목록조회와 상세조회만 가능합니다. 
3. 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어야하고, 목록조회와 상세조회시에 예약상태를 포함해야합니다.
4. 제품의 상태는 "판매중", "예약중", "완료" 세가지가 존재합니다. 
5. 구매자가 제품의 상세페이지에서 구매하기 버튼을 누르면 거래가 시작됩니다. 
6. 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다. 
7. 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있습니다.
8. 판매자는 거래진행중인 구매자에 대해 '판매승인'을 하는 경우 거래가 완료됩니다.


<br>

##### 2단계 (선택)
9. 제품에 수량이 추가됩니다. 제품정보에 "제품명", "가격", "예약상태", "수량"이 포함되어야합니다. 
10. 다수의 구매자가 한 제품에 대해 구매하기가 가능합니다. (단, 한 명이 구매할 수 있는 수량은 1개뿐입니다.)
11. 구매확정의 단계가 추가됩니다. 구매자는 판매자가 판매승인한 제품에 대해 구매확정을 할 수 있습니다. 
12. 거래가 시작되는 경우 수량에 따라 제품의 상태가 변경됩니다. 
    - 추가 판매가 가능한 수량이 남아있는 경우 - 판매중
    - 추가 판매가 불가능하고 현재 구매확정을 대기하고 있는 경우 - 예약중
    - 모든 수량에 대해 모든 구매자가 모두 구매확정한 경우 - 완료
13. "구매한 용품"과 "예약중인 용품" 목록의 정보에서 구매하기 당시의 가격 정보가 나타나야합니다. 
    - 예) 구매자 A가 구매하기 요청한 당시의 제품 B의 가격이 3000원이었고 이후에 4000원으로 바뀌었다 하더라도 목록에서는 3000원으로 나타나야합니다.

<hr>

### 1. 제품 등록과 구매는 회원만 가능합니다.
##### - Spring Security의 form 로그인 방식으로 구현 해서 response가 로그인 페이지로 나옵니다. 
![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/18d662de-3fcd-494c-91f3-9c14e940085b)

##

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/9ed07c5a-e9dd-4615-beb3-e16fc04a1ebf)

<hr>

### 2. 비회원은 등록된 제품의 목록조회와 상세조회만 가능합니다.

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/334ac8bf-3773-4bae-adda-5b849c3665e0)


##
![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/725ca8a1-9866-41da-b1d5-fa8a75ada575)

<hr>

### 3. 등록된 제품에는 "제품명", "가격", "예약상태"가 포함되어야하고, 목록조회와 상세조회시에 예약상태를 포함해야합니다.

#### - 2. 참조

<hr>

### 4. 제품의 상태는 "판매중", "예약중", "완료" 세가지가 존재합니다. 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/6912f7ec-5f47-4fab-b082-6353b5f56b82)

<hr>

### 5. 구매자가 제품의 상세페이지에서 구매하기 버튼을 누르면 거래가 시작됩니다

##### 시나리오 4번 참조

<hr> 

### 6. 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인할 수 있습니다.

##### 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 "모든" 거래 내역을 확인 할 수 있습니다.

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/8691170f-3c62-4ddb-b1e1-df5b5a52a23f)


<hr>

### 7. 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있습니다.

#### - 구매확정을 눌렀을 때 구매한 용품 (내가 구매) 시나리오 1의 "test2" 계정으로 로그인 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/3f9e482a-221e-4b07-885a-916126ce9073)

##

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/02b57b25-666e-44db-8aed-7b1c62859b3f)


##

#### - 예약중인 용품(내가 구매자/판매자 모두)의 목록 확인

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/43dcd15a-4f53-4445-a17c-7e1d142c1280)

<hr> 

### 8. 판매자는 거래진행중인 구매자에 대해 '판매승인'을 하는 경우 거래가 완료됩니다.

##### 시나리오 참조

<hr>

### 9. 제품에 수량이 추가됩니다. 제품정보에 "제품명", "가격", "예약상태", "수량"이 포함되어야합니다. 

##### 시나리오 참조

<hr>

### 10. 다수의 구매자가 한 제품에 대해 구매하기가 가능합니다. (단, 한 명이 구매할 수 있는 수량은 1개뿐입니다.)

##### 10-1. "test3"으로 로그인 후 시나리오의 1번 아이템 구매 예약

##### 10-2. item_id의 번호 확인 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/17502be4-b816-4dcf-a934-5df4dbc6ccea)

##

##### 10-3. (단, 한 명이 구매할 수 있는 수량은 1개뿐입니다.)

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/4cbc3889-c47d-488c-b1e9-a0b32c8d9cb2)


### 11. 구매확정의 단계가 추가됩니다. 구매자는 판매자가 판매승인한 제품에 대해 구매확정을 할 수 있습니다. 

##### 시나리오 참조

### 12. 거래가 시작되는 경우 수량에 따라 제품의 상태가 변경됩니다. 
    - 추가 판매가 가능한 수량이 남아있는 경우 - 판매중
    - 추가 판매가 불가능하고 현재 구매확정을 대기하고 있는 경우 - 예약중
    - 모든 수량에 대해 모든 구매자가 모두 구매확정한 경우 - 완료

### process 
-  1. 구매자가 예약을 시작할 때 제품의 수량 -1 
-  2-1. 제품이 1개 이상일 경우 - 판매중
-  2-2. 제품이 0개일경우 && buy 테이블에서 특정 아이템의 구매확정 갯수와 특정 아이템의 갯수가 동일하지 않다면 - 예약중
-  2-3  buy 테이블에서 특정 아이템의 구매확정 갯수와 특정 아이템의 갯수가 동일하다면 - 완료 

### 12-1. 시나리오대로 test1의 계정으로 quantity 3의 아이템 생성

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/f1364da6-9862-4404-81cc-6783fa8070b8)


### 12-2. "test2" , "test3" ,"test4"의 아이디로 진행 

### 12-3. "test2"의 계정으로 로그인후 예약을 시작할때 -1 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/6953fd83-d133-463f-970c-744395d0aa55)


### 12-4 "test2", "test3", "test4" 아이디로 모든 아이템 예약시 item_state = RESERVED로 상태값 변경

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/4cf71cee-4d5e-4e78-a2f4-1a8b4a7e3aec)


### 12-5 "test4" 계정으로 아이템 확정시 

![image](https://github.com/Jjd3109/wanted-preonboarding-challenge-backend-20/assets/100771092/45394090-7321-4726-abc6-ec001f46de5a)

sold로 바뀜



