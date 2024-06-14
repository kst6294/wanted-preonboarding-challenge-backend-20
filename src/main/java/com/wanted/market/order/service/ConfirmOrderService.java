package com.wanted.market.order.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.order.domain.StockRequester;
import com.wanted.market.order.exception.OutOfStockException;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class ConfirmOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;
    private final StockRequester stockRequester;

    public ConfirmOrderService(OrderRepository orderRepository, StockRequester stockRequester) {
        this.orderRepository = orderRepository;
        this.stockRequester = stockRequester;
    }

    /**
     * product의 상태: ON_SALE -> RESERVED -> SOLD (-> ON_SALE)
     * 재고가 소진되었을 때: OutOfStockEventListener에서 (동시성 제어를 위해) StockManager로부터 Lock을 획득한 뒤 product의 status를 RESERVED로 바꾸자
     * 주문들이 모두 구매확정되었을 때: SoldOutEventListener에서 마찬가지로 Lock을 획득한 뒤 product의 status를 SOLD로 바꾸자
     *
     * @param cmd
     * @throws NotFoundException
     * @throws UnauthorizedRequestException
     * @throws OutOfStockException
     */
    @Timed("ConfirmOrderService.confirm")
    public void confirm(Command cmd) throws NotFoundException, UnauthorizedRequestException, OutOfStockException, InvalidRequestException {
        logger.info("start find order entity: {}", cmd);
        Order findOrder = orderRepository.findByIdOrThrow(cmd.id());
        logger.info("current order status: {}", findOrder.getStatus());
        findOrder.confirm(cmd.userId(), (productId, sellerId) -> Objects.equals(orderRepository.findSellerIdByProductId(productId), sellerId), stockRequester);
        logger.info("status changed to {}", findOrder.getStatus());
    }

    public record Command(
            Long id,
            Long userId
    ) {
    }
}
