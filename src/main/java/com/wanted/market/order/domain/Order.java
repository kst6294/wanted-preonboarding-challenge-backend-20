package com.wanted.market.order.domain;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.global.event.Events;
import com.wanted.market.order.domain.vo.Status;
import com.wanted.market.order.event.OrderConfirmedEvent;
import com.wanted.market.order.event.OrderFinishedEvent;
import com.wanted.market.order.exception.OutOfStockException;
import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS", indexes = {@Index(name = "buyer_id_idx", columnList = "buyerId"), @Index(name = "product_id_idx", columnList = "productId")}, uniqueConstraints = {@UniqueConstraint(name = "unique_buyer_id_and_product_id", columnNames = {"buyerId", "productId"})})
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
     * 따라서 {@link com.wanted.market.product.domain.Product#order(Long, Integer, com.wanted.market.product.domain.DuplicateBuyerChecker)}에 Factory 매서드를 구현해두었으니 이를 통해 Order를 생성하시고, 직접 호출은 삼가주세요.
     *
     * @param buyerId   주문자 id
     * @param productId 주문하는 상품 id
     * @param price     상품의 현재 가격
     * @see com.wanted.market.product.domain.Product#order(Long, Integer, com.wanted.market.product.domain.DuplicateBuyerChecker)
     */
    public Order(Long buyerId, Long productId, Integer price) throws InvalidRequestException {
        if (buyerId == null || buyerId <= 0)
            throw new InvalidRequestException("buyerId must be greater than 0");
        if (productId == null || productId <= 0)
            throw new InvalidRequestException("productId must be greater than 0");
        if (price == null || price <= 0)
            throw new InvalidRequestException("price must be greater than 0");
        this.buyerId = buyerId;
        this.productId = productId;
        this.price = price;
        this.status = Status.REQUESTED;
    }

    /**
     * Product의 상태변경을 위해 재고가 소진된 경우 {@link com.wanted.market.order.event.OrderConfirmedEvent}를 발행해주세요.
     *
     * @param loginMemberId  판매자 id
     * @param sellerChecker  주문을 확정할 권한이 있는 판매자인지 확인하기 위한 인터페이스
     * @param stockRequester 재고를 할당받기 위해 재고관리서비스로부터 재고를 받아오는 인터페이스
     * @return 주문 확정 후 남아있는 재고량
     * @throws UnauthorizedRequestException 현재 접속중인 사용자의 id가 상품의 판매자의 id와 일치하지 않는 경우
     * @throws OutOfStockException          재고가 없는 경우
     */
    public void confirm(Long loginMemberId, SellerChecker sellerChecker, StockRequester stockRequester) throws UnauthorizedRequestException, OutOfStockException, InvalidRequestException {
        if (this.status != Status.REQUESTED)
            throw new InvalidRequestException("Order has already been confirmed");
        if (!sellerChecker.check(this.productId, loginMemberId))
            throw new UnauthorizedRequestException("Only seller can confirm order");
        Integer leftStock = stockRequester.request(this.productId);
        this.status = Status.CONFIRMED;
        Events.publish(new OrderConfirmedEvent(this.id, this.productId, leftStock));
    }

    /**
     * Product의 상태변경을 위해 {@link com.wanted.market.order.event.OrderFinishedEvent}를 발행해주세요.
     *
     * @param buyerId 주문자 id
     */
    public void finish(Long buyerId) throws UnauthorizedRequestException, InvalidRequestException {
        if (!this.buyerId.equals(buyerId))
            throw new UnauthorizedRequestException("Only buyer can finish order");
        if (!this.status.equals(Status.CONFIRMED))
            throw new InvalidRequestException("Order has to be confirm before finish");
        this.status = Status.FINISHED;
        Events.publish(new OrderFinishedEvent(this.id, this.productId));
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Status getStatus() {
        return status;
    }
}
