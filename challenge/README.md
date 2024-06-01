# ERD

![image](https://github.com/kst6294/wanted-preonboarding-challenge-backend-20/assets/61442066/f6b53413-35e9-49e2-a555-4afdd5e495b5)
https://www.erdcloud.com/d/niZoHXELKi22SdLsZ

# 요구사항 구현

## 1단계

### 제품 등록과 구매는 회원만 가능 / 비회원은 목록조회와 상세조회만 가능

Spring Security를 통해 목록조회 및 상세조회만 전체 허용하고 나머지 api는 허용하지 않았습니다.

```java
authorizeRequests.requestMatchers(HttpMethod.GET,"/products","/products/{productId:[\\d+]}")
        .permitAll();
        authorizeRequests.anyRequest()
        .authenticated();
```

### 제품에 제품명, 가격, 예약상태 포함 / 예약상태는 판매중, 예약중, 완료가 존재

제품을 나타내는 Product entity에 해당 필드들을 포함하였습니다.

```java
@Column(columnDefinition = "VARCHAR(100)")
private String name;  // 제품명

@Convert(converter = PriceConverter.class)
private Price price;  // 가격

@Enumerated(EnumType.STRING)
@Column(name = "reservation_code")
private Reservation reservation;  // 예약상태
```

예약상태에는 enum으로 판매중, 예약중, 완료를 나타내도록 했습니다.

```java
public enum Reservation {
    SALE, RESERVE, COMPLETE;
}
```

### 제품 상세페이지에서 구매하기 버튼을 누르면 거래가 시작

구매하기 버튼을 누를 시 `/products/purchase` api로 요청을 전송합니다.

```java
public record PurchaseRequest(@NotNull Long productId) {
}
```

해당 요청은 제품 번호를 포함합니다.

```java
@PostMapping("/purchase")
public ResponseEntity<Void> purchase(@RequestBody @Valid PurchaseRequest purchaseRequest,
@AuthenticationPrincipal AccountDetail accountDetail){

        productService.purchase(purchaseRequest.productId(),accountDetail.getAccountId());

        return ResponseEntity.ok()
        .build();
        }
```

사용자 판별은 `@AuthenticationPrincipal`을 통해 현재 로그인된 사용자의 정보를 가져옵니다.
이후 Transacts 테이블에 거래 정보를 만들고 저장합니다.

### 판매자와 구매자는 제품의 상세정보를 조회하면 당사자간의 거래내역을 확인 가능

구매자는 본인의 구매정보만 확인하면 되겠지만, 판매자는 모든 구매자들과의 내역을 확인할 수 있도록 작성했습니다.

#### 비회원

```java
if(optAccountId.isEmpty()){
        return new ProductDetailResponse(product,Collections.emptyList());
        }
```

제품 정보를 담되, 거래내역은 빈 리스트를 보내도록 하였습니다.

#### 구매자

```java
return transactRepository.retrieveAllTransactState(buyerId,productId)
        .stream()
        .map(PurchaseDetailResponse::new)
        .map(PurchaseInfo.class::cast)
        .toList();
```

구매자 id와 제품 id로 일치하는 거래내역들을 가져온 후 반환합니다.

#### 판매자

```java
List<Transact> transacts=transactRepository.findByProductId(productId);
        Map<Long, List<TransactState>>buyerTransactStates=getBuyerTransactStates(transacts);

        return getPurchaseInfos(buyerTransactStates);
```

해당 제품 번호로 이루어진 모든 거래를 조회한 후 Map<사용자 번호, 거래내역>으로 데이터를 조회합니다.
그 후 response에 맞게 형변환하여 반환합니다.
거래내역은 최신순으로 가져옵니다.

### 내가 구매한 용품(구매자)과 예약중인 용품(구매자, 판매자)의 목록을 확인

사용자가 구매 기록 확인 시 모든 거래 기록(구매, 예약중 포함)을 확인할 수 있도록 했습니다.
사용자가 예약중인 용품 확인 시 해당 사용자가 판매하고 있는 제품 중 예약 상태인 제품들만 확인할 수 있도록 했습니다.

### 거래진행중인 구매자에 대해 판매승인

```java
List<TransactState> transactStates=transactRepository.retrieveAllTransactState(transact.getId());

        if(transactStates.contains(TransactState.APPROVE)){
        throw new CustomException(ExceptionStatus.APPROVE_ALREADY);
        }

        transactLogRepository.save(new TransactLog(transact,TransactState.APPROVE));
```

사용자가 이전에 판매승인한 적이 없다면 판매승인 기록을 작성합니다.

## 2단계

### 제품에 수량 추가

```java
@Convert(converter = QuantityConverter.class)
private Quantity quantity;
```

### 다수의 구매자가 한 제품에 대해 구매 가능(한 명이 구매 가능한 수량은 단 1개뿐)

```java
Optional<Transact> optTransact=transactRepository.findByBuyerIdAndProductId(buyerId,productId);

        if(optTransact.isPresent()){
        throw new CustomException(ExceptionStatus.PURCHASE_ALREADY);
        }
```

거래정보가 있다면 제품을 구매할 수 없도록 했습니다.
하나의 거래 정보는 제품 하나를 산 것과 동일합니다.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Product> findProductWithUpdateLockById(Long productId);
```

다수의 구매자가 한 제품을 동시에 구매하는 경우 수량이 제대로 업데이트되지 않을 수 있습니다. 따라서 Lock을 걸어 수량 업데이트가 정상적으로 될 수 있도록 하였습니다.

### 구매자는 판매승인된 제품에 대해 구매확정 가능

```java
TransactState lastTransactState=transactRepository.retrieveLastTransactState(buyerId,productId);

        if(lastTransactState!=TransactState.APPROVE){
        throw new CustomException(ExceptionStatus.CAN_NOT_CONFIRM);
        }
```

마지막 거래기록을 가져와 판매승인되었는지 확인합니다.
판매승인되지 않았다면 예외를 발생시키고, 승인된 상태라면 구매확정 기록을 작성합니다.

### 수량에 따라 제품 상태 변경

#### 수량이 남아있는 경우 판매중

초기 수량은 0으로 지정할 수 없으며, 제품 등록 시 초기 제품 상태는 판매중입니다.

#### 추가 판매 불가능, 구매확정 대기 시 예약중

```java
public void purchase(){
        this.quantity=Quantity.minus(this.quantity);

        if(quantity.value()==0&&this.reservation==Reservation.SALE){
        this.reservation=Reservation.RESERVE;
        }
        }
```

제품 판매 시 남은 수량이 0이고 제품 상태가 판매중이었을 경우 예약중으로 변경됩니다.

#### 모든 구매자가 구매확정한 경우 완료

```java
Set<TransactState> transactStates=transactRepository.retrieveDistinctProductTransactStates(product);

        if(transactStates.size()==1&&transactStates.contains(TransactState.CONFIRM)){
        product.complete();
        }
```

구매 확정 시 해당 제품에 대해 모든 마지막 거래 기록이 확정밖에 없을 경우 해당 제품은 완료 상태로 변경됩니다.

### 구매한 용품, 예약중인 용품 목록 정보에서 구매 당시의 가격 정보를 보여야 함

```java
public class Transact extends BaseEntity {
    ...
    @Convert(converter = PriceConverter.class)
    private Price price;

    public Transact(Account buyer, Product product) {
        this.buyer = buyer;
        this.product = product;
        this.price = product.getPrice();
    }
}
```

거래 entity 생성 단계에서 거래 당시 가격을 기록하도록 하였습니다. 구매한 용품, 예약중인 용품 목록 정보에서 해당 값을 사용하도록 하였습니다.

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

### 구매 확정

#### POST /transacts/confirm

> productId: 제품 번호

# 추가적인 구현

## 제품 상세정보 조회

- 제품의 상세정보 조회 시 당사자간의 거래내역을 확인할 수 있어야 합니다. 구매자인 경우 거래 내역을 순차적으로 확인, 판매자인 경우 최신 순으로 이뤄진 거래에 대해 구매자 정보로 그룹핑하여 조회할 수 있도록
  하였습니다.
- 구매자가 많을 경우 페이지네이션을 적용하는 게 효율적이겠으나, 제품 상세정보 조회가 아닌 제품 구매정보 / 제품 판매정보 기능이 따로 분리되는 게 우선이라고 생각합니다. 따라서 페이지네이션까지는 구현하지
  않았습니다.

# 고민

## 제품 예약상태 및 수량

제품의 수량과 사용자들의 구매확정에 대해 제품 내의 예약상태가 변경되는 게 옳은 방향인지에 대한 고민이 있었습니다. 전체 수량 - 구매 수 / 전체 수량 - 구매 확정수로 상태를 판단하고, 수량은 현재 수량이 아닌
지금까지의 전체 수량으로 계산하는 게 어떨까 하는 생각이 있었습니다. 다만 요구사항 자체에 해당 데이터들이 포함되어야 한다고 기재되어 있었기에 이를 따랐습니다.
모든 구매자가 구매 확정 시 이벤트를 사용하여 제품의 상태를 변화시키는 게 좋다고 생각했으나 오히려 리소스를 더 잡아먹을 수 있을 것 같다는 생각에 지금과 같은 구조로 구현했습니다.
