package com.wanted.market.product.domain;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.order.domain.Order;
import com.wanted.market.product.domain.vo.Status;
import com.wanted.market.product.exception.DuplicateOrderException;
import com.wanted.market.product.exception.OutDatedProductVersionException;
import jakarta.persistence.*;

/**
 * <h3>Product와 Order는 같은 aggregate에 속하는가?</h3>
 * <ol>
 *     <li>Order의 생성을 위해 Product의 정보(id/price 등)을 조회해야 한다.</li>
 *     <li>Order의 생성/변경이 발생하면 Product의 status를 갱신해주어야 한다.</li>
 * </ol>
 * <p>
 * 같은 aggregate로 관리하게 되면 Order를 조회하기 위해 Product의 모든 Order를 불러와야 한다.
 * 하지만 (Product에 속한 Order의 개수)*(Order 크기) 정도의 데이터를 불러오는 것이 (비효율적일 수는 있지만) 심각한 부하를 발생시키지는 않는다.
 * </p>
 * <p>
 * 반면에 Order를 변경(삭제는 고려요소가 아님)하게 되면 해당 Product에 속한 Order를 모두 삭제한 뒤 bulk insert를 수행하기 때문에 I/O burst가 발생할 우려가 있다.
 * 또한 Order의 생성/변경에 의해 Product의 version이 변경되기 떄문에 (제품명/가격과 같은) “상품만의 변경이력"을 관리하기 힘들어진다.
 * </p>
 * <p>따라서 별도의 aggregate로 Product와 Order를 관리하겠다.</p>
 * <br>
 *
 * <h3>동시성 제어</h3>
 * <p>Product의 버전 변경요인은</p>
 * <ol>
 *     <li>"판매자 요청"으로 인한 <b>정보변경</b></li>
 *     <li>"신규주문"으로 인한 <b>재고변경</b></li>
 *     <li>"주문확정"으로 인한 <b>상태변경</b></li>
 * </ol>
 * <p>"신규주문" 및 "주문확정"이 발생하면 Product의 재고/상태를 변경해주어야 하지만 그로 인해 "판매자 요청"으로 인한 정보변경(이하 정보변경)이 덮어씌워져서는 안된다.
 *
 * <h5>해결방안 - Mutex-lock(or Pessimistic-lock/X-lock)</h5>
 * <p>
 * Mutex-lock(or Pessimistic-lock/X-lock)으로 동시성 제어한다.
 * 즉, lock을 취득하기 전에는 Product를 조회를 할 수 없다.
 * 하지만 주문을 Mutex-lock(or Pessimistic-lock/X-lock)으로 통제하게 되면 성능상 치명적일 수 있기 때문에 재고변경의 경우 별도의 lock 전략을 고민해야 한다.
 * </p>
 *
 * <h5>해결방안 - Optimistic-lock</h5>
 * <p>Optimistic-lock으로 동시성 제어한다.</p>
 * <ol>
 *     <li>"신규주문" vs "신규주문"의 경합관계:</li>
 *     "신규주문"이 발주될 때마다 재고에(경우에 따라 상태도) update가 발생하고, 그로 인해 잦은 경합이 발생하게 된다.
 *     또한 경합관계의 "신규주문"들이 같은 시점의 Product를 조회해왔다면 (재고가 1밖에 줄어들지 않아) 초과구매가 될 우려가 있으므로 경합발생시 Product를 다시 조회/변경해야하는 문제가 발생한다.
 *     <li>"신규주문" vs "정보변경"의 경합관계:</li>
 *     반면에 정보변경과 경합이 발생한 경우, 후자가 항상 우선되어야 한다.
 *     하지만 Optimistic-lock으로는 우선순위를 부여할 수 없다.
 * </ol>
 *
 * <h5>(*)해결방안 - 재고관리서비스(및 재고변경를 위한 lock)과 정보변경 버전관리를 분리</h5>
 * <ol>
 *     <li>Product의 버전은 오로지 상품의 정보변경이력을 추적하기 위한 용도로만 사용하기로 한다.</li>
 *     다시 말하면, 구매자가 보고 있는 정보가 최신의 정보인지 확인하기 위한 용도로 사용한다.
 *     <li>동시성 제어는 재고관리서비스에서 담당한다.</li>
 *     (재고를 넘어서는) 초과구매가 발생하지 않도록 재고관리를 위한 서비스를 별도로 운용한다.
 *     <b>"판매자가 재고를 변경하고자 하는 경우에는 재고관리서비스의 lock을 (해당 Product의 stock 레코드에 락을 획득하는 방식으로) 먼저 획득해야 한다.</b>
 * </ol>
 *
 * @see #order(Long, Integer, DuplicateBuyerChecker)
 */
@Entity
@Table(name = "PRODUCT", indexes = {@Index(name = "seller_id_idx", columnList = "sellerId")})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version;
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    protected Product() {
    }

    /**
     * 준비상태의 상품을 등록합니다.
     * {@link #open(StockRegister)}를 호출하여 재고를 등록해주어야 판매가능합니다.
     *
     * @param sellerId 판매자 id
     * @param name     상품 이름
     * @param price    상품 가격
     * @param quantity 상품 수량
     */
    public Product(Long sellerId, String name, Integer price, Integer quantity) throws InvalidRequestException {
        if (sellerId == null || sellerId < 0)
            throw new InvalidRequestException("SellerId cannot be negative");
        if (name == null || name.isBlank())
            throw new InvalidRequestException("Name cannot be null or empty");
        if (price == null || price < 0)
            throw new InvalidRequestException("Price cannot be negative");
        if (quantity == null || quantity <= 0)
            throw new InvalidRequestException("Quantity cannot be zero or negative");
        this.sellerId = sellerId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = Status.PREPARING;
    }

    /**
     * 재고를 등록해서 상품의 판매를 게시합니다.
     *
     * @param stockRegister 재고 등록 서비스
     */
    public void open(StockRegister stockRegister) {
        stockRegister.register(this.id, this.quantity);
        this.status = Status.ON_SALE;
    }

    /**
     * 신규 주문
     *
     * @param buyerId               구매자 id
     * @param version               구매자가 보고 있는 상품정보의 버전
     * @param duplicateBuyerChecker 중복구매를 확인하기 위한 인터페이스
     * @return 신규 주문(Order)
     * @throws OutDatedProductVersionException 클라이언트가 보고 있는 상품의 정보에 변동이 있는 경우
     */
    public Order order(Long buyerId, Integer version, DuplicateBuyerChecker duplicateBuyerChecker) throws InvalidRequestException, OutDatedProductVersionException {
        if (sellerId.equals(buyerId))
            throw new InvalidRequestException("Seller can't reserve his/her own product");
        if (!this.version.equals(version))
            throw new OutDatedProductVersionException();
        if (duplicateBuyerChecker.check(this.id, buyerId))
            throw new DuplicateOrderException("Buyer already has this product");
        return new Order(buyerId, this.id, this.price);
    }

    public void reserved() {
        this.status = Status.RESERVED;
    }

    public void sold() throws InvalidRequestException {
        if(this.status == Status.RESERVED)
            this.status = Status.SOLD;
        throw new InvalidRequestException("Product status must be reserved");
    }

    public Long getId() {
        return id;
    }

    public boolean isReserved() {
        return this.status == Status.RESERVED;
    }
}
