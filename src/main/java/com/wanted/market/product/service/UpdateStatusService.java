package com.wanted.market.product.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.order.domain.vo.Status;
import com.wanted.market.order.event.OrderConfirmedEvent;
import com.wanted.market.order.event.OrderFinishedEvent;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 상품 정보 수정 vs 상품 상태 수정
 * TODO: X-lock으로 처리
 */
@Service
@Transactional
public class UpdateStatusService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UpdateStatusService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void toReserved(OrderConfirmedEvent event) throws NotFoundException {
        logger.info("caught OrderConfirmedEvent");
        if (event.leftStock() <= 0) {
            logger.info("out of stock!");
            Product findProduct = productRepository.findByIdOrThrow(event.productId());
            findProduct.reserved();
            logger.info("change status to reserved");
        } else {
            logger.info("stock left, leave status");
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void toSold(OrderFinishedEvent event) throws NotFoundException, InvalidRequestException {
        logger.info("caught OrderFinishedEvent");
        if (!orderRepository.existsByProductIdAndStatus(event.productId(), Status.CONFIRMED)) {
            logger.info("every confirmed order is done");
            Product findProduct = productRepository.findByIdOrThrow(event.productId());
            findProduct.sold();
            logger.info("change status to sold");
        } else {
            logger.info("confirmed order left, leave status");
        }
    }
}
