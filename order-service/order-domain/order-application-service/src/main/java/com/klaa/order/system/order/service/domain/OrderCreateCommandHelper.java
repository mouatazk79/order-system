package com.klaa.order.system.order.service.domain;
import com.klaa.order.system.domain.order.service.domain.OrderDomainService;
import com.klaa.order.system.order.service.domain.outbox.sheduler.driver.DriverOutboxHelper;
import com.klaa.order.system.order.service.domain.ports.output.repository.UserRepository;
import com.klaa.order.system.order.service.domain.dto.create.OrderCreateCommand;
import com.klaa.order.system.domain.order.service.domain.entity.Driver;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.entity.User;
import com.klaa.order.system.domain.order.service.domain.event.OrderCreatedEvent;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.order.service.domain.mapper.OrderDataMapper;
import com.klaa.order.system.order.service.domain.ports.output.repository.DriverRepository;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.outbox.OutboxStatus;
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
    private final DriverOutboxHelper driverOutboxHelper;
    private final SagaHelper sagaHelper;

    @Transactional
    public OrderCreatedEvent persistOrder(OrderCreateCommand orderCreateCommand) {
        checkUser(orderCreateCommand.getUserId());
        Order order=orderDataMapper.orderCreateCommandToOrder(orderCreateCommand);
        Driver driver=getDriver(new DriverId(UUID.fromString("d475508c-2c8a-404a-9e5d-750292e8eb75")));
        OrderCreatedEvent orderCreatedEvent=orderDomainService.validateAndInitiateOrder(order,driver);
        saveOrder(orderCreatedEvent.getOrder());
        driverOutboxHelper.saveDriverRequestOutboxMessage(
                orderDataMapper.orderCreatedEventToDriverRequestPayload(orderCreatedEvent),
                orderCreatedEvent.getOrder().getOrderStatus(),
                sagaHelper.orderStatusToSagaStatus(orderCreatedEvent.getOrder().getOrderStatus()),
                OutboxStatus.STARTED,
                UUID.randomUUID()
                );

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
        Optional<User> user=userRepository.findUserById(id);
        if(user.isEmpty()){
            throw new OrderDomainException("user with id :"+id+"does not exist");
        }
    }

    private void saveOrder(Order order){
        Optional<Order> newOrder=orderRepository.saveOrder(order);
        if (newOrder.isEmpty()){
            throw new OrderDomainException("order :"+newOrder+" not saved");
        }
    }
}
