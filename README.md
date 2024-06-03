## 원티드 프리온보딩 챌린지 백엔드 20 사전과제


### API 명세

##### 회원

|제목|HTTP|URL|파라미터 타입|반환형태|
|---|---|---|---|---|
|회원가입|POST|/member|MemberSignDto|ResponseEntity<Long>|

##### 제품

|제목|HTTP|URL|파라미터 타입|반환형태|
|------|---|---|---|---|
|제품등록|POST|/item|MemberDetails, ItemAddDto|ResponseEntity<Long>|

##### 주문

|제목|HTTP|URL|파라미터 타입|반환형태|
|------|---|---|---|---|
|주문하기|POST|/member|MemberDetails ,OrderAddDto|ResponseEntity<Long>|
|판매승인|POST|/salesApproval|OrderSalesApprovalDto|ResponseEntity|
|구매확정|POST|/purchaseConfirmation|OrderPurchaseConfirmationDto|ResponseEntity|

##### 화면

|제목|HTTP|URL|파라미터 타입|반환형태|
|------|---|---|---|---|
|제품목록|GET|/items|MemberDetails memberDetails|String|
|제품상세|GET|/item/{id}|MemberDetails, Long|String<Long>|
|구매상품목록|GET|/buyItems|MemberDetails|String|
|예약상품목록|GET|/reservationItems|MemberDetails|String|
