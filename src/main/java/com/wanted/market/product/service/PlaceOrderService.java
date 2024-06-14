package com.wanted.market.product.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.product.domain.DuplicateBuyerChecker;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductRepository;
import com.wanted.market.product.exception.DuplicateOrderException;
import com.wanted.market.product.exception.OutDatedProductVersionException;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlaceOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final DuplicateBuyerChecker duplicateBuyerChecker;

    public PlaceOrderService(ProductRepository productRepository, OrderRepository orderRepository, DuplicateBuyerChecker duplicateBuyerChecker) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.duplicateBuyerChecker = duplicateBuyerChecker;
    }

    /**
     * 상품 주문
     *
     * @param cmd 주문 요청 command
     * @return 주문 id
     * @throws NotFoundException 존재하지 않는 상품을 주문하는 경우
     * @throws InvalidRequestException 잘못된 요청 또는 중복 주문을 하는 경우
     * @throws OutDatedProductVersionException 정보변경 전의 상품을 주문하는 경우
     */
    @Timed("PlaceOrderService.order")
    public Long order(Command cmd) throws NotFoundException, InvalidRequestException, OutDatedProductVersionException {
        logger.info("start place new order: {}", cmd);
        Product findProduct = productRepository.findByIdOrThrow(cmd.productId());
        Order newOrder = findProduct.order(cmd.buyerId(), cmd.version(), duplicateBuyerChecker);
        try {
            orderRepository.saveAndFlush(newOrder);
            logger.info("order placed: {}", newOrder.getId());
        } catch (DuplicateKeyException e) {
            logger.warn("duplicate order exists for product: {}", findProduct.getId());
            throw new DuplicateOrderException(e.getMessage(), e);
        }
        return newOrder.getId();
    }

    public record Command(
            Long productId,
            Integer version,
            Long buyerId
    ) {
    }
}
