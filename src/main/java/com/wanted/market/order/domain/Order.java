package com.wanted.market.order.domain;

import com.wanted.market.order.domain.vo.Status;
import com.wanted.market.product.domain.DuplicateBuyerChecker;
import com.wanted.market.product.domain.StockManager;
import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS", indexes = {@Index(name = "buyer_id_idx", columnList = "buyerId"), @Index(name = "product_id_idx", columnList = "productId")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"buyerId", "productId"})})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "price", nullable = false)
    private Integer price;
    @Enumerated(EnumType.STRING)
    private Status status;

    protected Order() {
    }

    /**
     * 주문(Order)를 생성하기 전에 상품정보변경(version) 및 재고(stock)여부를 확인해야 합니다.
     * 따라서 {@link com.wanted.market.product.domain.Product#order(Long, Integer, DuplicateBuyerChecker, StockManager)}에 Order를 위한 Factory 매서드를 구현해두었으니 이를 통해 Order를 생성하시고, 직접 호출은 삼가주세요.
     *
     * @param buyerId   주문자 id
     * @param productId 주문하는 상품 id
     * @param price     상품의 현재 가격
     * @see com.wanted.market.product.domain.Product#order(Long, Integer, DuplicateBuyerChecker, StockManager)
     */
    public Order(Long buyerId, Long productId, Integer price) {
        if (buyerId == null || buyerId <= 0)
            throw new IllegalArgumentException("buyerId must be greater than 0");
        if (productId == null || productId <= 0)
            throw new IllegalArgumentException("productId must be greater than 0");
        if (price == null || price <= 0)
            throw new IllegalArgumentException("price must be greater than 0");
        this.buyerId = buyerId;
        this.productId = productId;
        this.price = price;
        this.status = Status.ON_GOING;
    }

    /**
     * Product의 상태변경을 위해 {@link com.wanted.market.order.event.OrderConfirmedEvent}를 발행해주세요.
     *
     * @param buyerId 주문자 id
     */
    public void confirm(Long buyerId) {
        if (!this.buyerId.equals(buyerId))
            throw new IllegalArgumentException("buyerId must be equal to this.buyerId");
        this.status = Status.CONFIRMED;
    }
}
