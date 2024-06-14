package com.wanted.market.order.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.domain.OrderRepository;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishOrderService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;

    public FinishOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Timed("FinishOrderService.finish")
    public void finish(Command cmd) throws NotFoundException, UnauthorizedRequestException, InvalidRequestException {
        logger.info("start find order entity: {}", cmd);
        Order findOrder = orderRepository.findByIdOrThrow(cmd.id());
        logger.info("current order status: {}", findOrder.getStatus());
        findOrder.finish(cmd.userId());
        logger.info("status changed to {}", findOrder.getStatus());
    }

    public record Command(
            Long id,
            Long userId
    ) {
    }
}
