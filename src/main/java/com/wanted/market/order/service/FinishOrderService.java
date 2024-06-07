package com.wanted.market.order.service;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.common.exception.UnauthorizedRequestException;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.domain.OrderRepository;
import com.wanted.market.order.event.OrderFinishedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FinishOrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FinishOrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public void finish(Command cmd) throws NotFoundException, UnauthorizedRequestException {
        Order findOrder = orderRepository.findByIdOrThrow(cmd.id());
        findOrder.finish(cmd.userId());
        eventPublisher.publishEvent(new OrderFinishedEvent(findOrder.getId(), findOrder.getProductId()));
    }

    public record Command(
            Long id,
            Long userId
    ) {
    }
}
