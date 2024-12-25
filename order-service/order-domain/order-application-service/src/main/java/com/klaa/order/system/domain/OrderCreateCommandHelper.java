package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.ports.output.publisher.driver.OrderCreatedRequestMessagePublisher;
import com.klaa.order.system.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.domain.valueobjects.DriverId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class OrderCreateCommandHelper {
    private final OrderDataMapper orderDataMapper;
    private final OrderDomainService orderDomainService;
    private final DriverRepository driverRepository;
    private final OrderRepository orderRepository;
    private final OrderCreatedRequestMessagePublisher orderCreatedRequestMessagePublisher;

    @Transactional
    public OrderCreatedEvent persistOrder(OrderCreateCommand orderCreateCommand) {
        Order order=orderDataMapper.orderCreateCommandToOrder(orderCreateCommand);
        order.initializeOrder();
        order.validateOrder();
        Driver driver=getDriver(order.getDriverId());
        OrderCreatedEvent orderCreatedEvent=orderDomainService.validateAndInitiateOrder(
                order,driver,orderCreatedRequestMessagePublisher
        );
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }


    public Driver getDriver(DriverId driverId){
        Optional<Driver> driver=driverRepository.findDriverById(driverId.getValue());
        if (driver.isEmpty()){
            throw new OrderDomainException("driver with id :"+driver.get().getId()+"does not exist");
        }
        return driver.get();
    }
    public Order saveOrder(Order order){
        Optional<Order> newOrder=orderRepository.saveOrder(order);
        if (newOrder.isEmpty()){
            throw new OrderDomainException("order with id :"+newOrder.get().getId()+"not saved");
        }
        return newOrder.get();

    }
}
