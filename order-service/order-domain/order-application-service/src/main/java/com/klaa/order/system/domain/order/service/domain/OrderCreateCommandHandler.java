package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.order.service.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.order.service.domain.mapper.OrderDataMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderCreateCommandHelper orderCreateCommandHelper;
    private final OrderDataMapper orderDataMapper;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateCommand orderCreateCommand) {
        OrderCreatedEvent orderCreatedEvent= orderCreateCommandHelper.persistOrder(orderCreateCommand);
        log.info("creating a new order {}",orderCreateCommand);
        return orderDataMapper.orderCreatedEventToOrderCreateResponse(orderCreatedEvent);
    }
}
