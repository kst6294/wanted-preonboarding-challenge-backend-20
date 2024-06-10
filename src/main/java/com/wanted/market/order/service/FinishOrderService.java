package com.wanted.market.order.service;

import com.wanted.market.common.exception.InvalidRequestException;
import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.global.event.Events;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.order.event.OrderFinishedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishOrderService {
    private final OrderRepository orderRepository;

    public FinishOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void finish(Command cmd) throws NotFoundException, UnauthorizedRequestException, InvalidRequestException {
        Order findOrder = orderRepository.findByIdOrThrow(cmd.id());
        findOrder.finish(cmd.userId());
        Events.publish(new OrderFinishedEvent(findOrder.getId(), findOrder.getProductId()));
    }

    public record Command(
            Long id,
            Long userId
    ) {
    }
}
