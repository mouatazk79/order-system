package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.dto.create.OrderCreateResponse;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.ports.output.publisher.driver.OrderCreatedRequestMessagePublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@AllArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderCreateCommandHelper orderCreateCommandHelper;
    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedRequestMessagePublisher orderCreatedRequestMessagePublisher;
    public OrderCreateResponse createOrder(OrderCreateCommand orderCreateCommand) {
        OrderCreatedEvent orderCreatedEvent= orderCreateCommandHelper.persistOrder(orderCreateCommand);
        log.info("creating a new order {}",orderCreateCommand);
        orderCreatedRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderCreatedEventToOrderCreateResponse(orderCreatedEvent);
    }
}
