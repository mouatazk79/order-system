package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.entity.Driver;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.entity.User;
import com.klaa.order.system.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.ports.output.publisher.driver.OrderCreatedRequestMessagePublisher;
import com.klaa.order.system.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.domain.ports.output.repository.UserRepository;
import com.klaa.order.system.domain.valueobjects.DriverId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class OrderCreateCommandHelper {
    private final OrderDataMapper orderDataMapper;
    private final OrderDomainService orderDomainService;
    private final DriverRepository driverRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderCreatedRequestMessagePublisher orderCreatedRequestMessagePublisher;

    @Transactional
    public OrderCreatedEvent persistOrder(OrderCreateCommand orderCreateCommand) {
        Order order=orderDataMapper.orderCreateCommandToOrder(orderCreateCommand);
        checkUser(orderCreateCommand.getCustomerId());
        order.initializeOrder();
        Driver driver=getDriver(order.getDriverId());
        order.validateOrder();
        OrderCreatedEvent orderCreatedEvent=orderDomainService.validateAndInitiateOrder(
                order,driver,orderCreatedRequestMessagePublisher
        );
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }
    private Driver getDriver(DriverId driverId){
        Optional<Driver> driver=driverRepository.findDriverById(driverId.getValue());
        if (driver.isEmpty()){
            throw new OrderDomainException("driver :"+driver+"does not exist");
        }
        return driver.get();
    }
    private void checkUser(UUID id){
        Optional<User> user=userRepository.findDriverById(id);
        if(user.isEmpty()){
            throw new OrderDomainException("user with id :"+id+"does not exist");
        }
    }

    private void saveOrder(Order order){
        Optional<Order> newOrder=orderRepository.saveOrder(order);
        if (newOrder.isEmpty()){
            throw new OrderDomainException("order :"+newOrder+"not saved");
        }
    }
}
