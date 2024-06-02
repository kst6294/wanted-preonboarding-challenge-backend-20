# API 명세서

## Member

 
`/api/member/signup` : 회원가입 GET

## Product

공통 mapping 주소 : `api/product`
`list`: 모든 제품 조회 GET
`/info/{product_id}` : 제품 상세 조회 GET
`/reservation/{product_id}` : 예약 물품 조회 GET
`/complete/{product_id}` : 완료된 물품 조회 GET

`"/confirm"` : 판매 승인 PUT

`api/product` : 제품 상태 변경 PUT
`/create` : 제품 등록 POST

# PurchaseList

공통 : `"/api/history"` 

`/add` : 구매하기 버튼 시  거래내역 등록 POST

`list/{member_id}` : 거래내역 조회 GET

# 요구사항 1단계(필수)

## 비회원은 목록조회와 상세조회만 가능/ 회원 제품 구매 및 등록

```jsx
http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
 .requestMatchers(HttpMethod.*GET*,"/api/product/list",
 "/api/product/info/{productId:[\\\\d+]}")   
             .permitAll()
             .anyRequest().authenticated())
```

## 제품에 제품명, 가격, 상태(판매, 예약,완료)

예약상태는 Enum클래스로 정의

```java
String product_name;

double price;

Status status;

public enum Status {    *complete*,    *sale*,    *reservation*}
```

제품 등록시 → 판매

구매하기 누룰 시 - > 예약중

판매승인 - > 완료

## 구매자가 상세페이지에서 구매버튼시 거래 시작

구매버튼 클릭시 

1. 거래 내역 등록 
2. 제품 상태 판매 → 예약중으로 변경

```java
public void addPurchasList(ProductReq productReq, UserDetailsImpl userDetails)
    {
        Member member = isExistUser(userDetails);
        if(member == null) return;

        //제품 상태 변경 : 판매 -> 예약
        productReq.setStatus(Status.reservation);
        productService.updateStatus(productReq,userDetails);

        PurchaseList purchaseList = new PurchaseList();
        //등록한 판매자의 아이디
        Long id = productReq.getMember().getUserId();

        purchaseList.setProduct_id(productReq.getProduct_id());
        purchaseList.setBuyerId(member.getUserId());
        purchaseList.setSellerId(id);
        purchaseList.setCreaeDate(LocalDate.now());

        this.purchaseListRepository.save(purchaseList);
    }
```

## 판매자와 구매자의 거래내역을 확인할 수 있다.

```java
public List<PurchaseList> getTransHistory(ProductReq productReq,Long id)
    {
		   
        //판매자인 경우
        if(productReq.getMember().getUserId().equals(id))
        {
            return this.purchaseListRepository.findAllBySellerId(id);
        }else{//구매자인 경우
            return this.purchaseListRepository.findAllByBuyerId(id);
        }
    }
```

## 모든 사용자는 내가 "구매한 용품(내가 구매자)"과 "예약중인 용품(내가 구매자/판매자 모두)"의 목록을 확인할 수 있습니다.

```java
@GetMapping("/reservation/{product_id}")
public ResponseEntity<?> getReservationOfProducts(@PathVariable Long product_id,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    List<Product> product = this.productService.getReservationsList(product_id,userDetails);

    return ResponseEntity.status(200).body(product);
}

@GetMapping("/complete/{product_id}")
public ResponseEntity<?> getCompleteOfProducts(@PathVariable Long product_id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
    List<Product> product = this.productService.getBoughtList(product_id,userDetails);

    return ResponseEntity.status(200).body(product);
}
```

## 판매자는 거래진행중인 구매자에 대해 '판매승인'을 하는 경우 거래가 완료됩니다.

판매승인 눌렀을 시 제품 complete로 변경

```java
public void permitSaleForProduct(ProductReq productReq, UserDetailsImpl userDetails)
{
    //제품 상태 변경 : 예약 -> 완료
    productReq.setStatus(Status.complete);
    updateStatus(productReq,userDetails);
    
    //거래내역 상태 변경
    purchaseListService.updateOrderStatus(productReq);
}
```

# 요구사항 2단계(선택)

## 제품에 수량 추가

```java
String product_name;

double price;

Status status;

int quantity;
```