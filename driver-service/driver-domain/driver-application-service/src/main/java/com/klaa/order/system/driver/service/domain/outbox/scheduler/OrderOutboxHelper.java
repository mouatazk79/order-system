package com.klaa.order.system.driver.service.domain.outbox.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderEventPayload;
import com.klaa.order.system.driver.service.domain.outbox.model.OrderOutboxMessage;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderOutboxRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Slf4j
@Component
public class OrderOutboxHelper {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;


    public OrderOutboxHelper(OrderOutboxRepository orderOutboxRepository, ObjectMapper objectMapper) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<OrderOutboxMessage> getOrderOutboxMessageBySagaIdAndOutboxStatus(UUID sagaId,
                                                                                              OutboxStatus
                                                                                                      outboxStatus) {
        return orderOutboxRepository.findByTypeAndSagaIdAndOutboxStatus("OrderProcessingSaga", sagaId, outboxStatus);
    }



    @Transactional(readOnly = true)
    public Optional<List<OrderOutboxMessage>> getOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        return orderOutboxRepository.findByTypeAndOutboxStatus("OrderProcessingSaga", outboxStatus);
    }
    @Transactional(readOnly = true)
    public Optional<List<OrderOutboxMessage>> getOrderOutboxMessageByDriverOrderStatusAndOutboxStatus(DriverOrderStatus driverOrderStatus,OutboxStatus outboxStatus) {
        return orderOutboxRepository.findByDriverOrderStatusNotAndOutboxStatus(driverOrderStatus, outboxStatus);
    }


    @Transactional
    public void deleteOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        orderOutboxRepository.deleteByTypeAndOutboxStatus("OrderProcessingSaga", outboxStatus);
    }

    @Transactional
    public void saveOrderOutboxMessage(OrderEventPayload orderEventPayload,
                                       DriverOrderStatus driverOrderStatus,
                                       OutboxStatus outboxStatus,
                                       UUID sagaId) {
        save(OrderOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .orderId(orderEventPayload.getOrderId())
                .driverId(orderEventPayload.getDriverId())
                .payload(createPayload(orderEventPayload))
                .createdAt(LocalDateTime.now())
                .processedAt(LocalDateTime.now())
                .type("OrderProcessingSaga")
                .driverOrderStatus(driverOrderStatus)
                .outboxStatus(outboxStatus)
                .build());
    }
    @Transactional
    public void saveOrderOutboxMessage(OrderOutboxMessage OrderOutboxMessage) {
        save(OrderOutboxMessage);
    }

    @Transactional
    public void updateOutboxStatus(OrderOutboxMessage orderPaymentOutboxMessage, OutboxStatus outboxStatus) {
        orderPaymentOutboxMessage.setOutboxStatus(outboxStatus);
        save(orderPaymentOutboxMessage);
        log.info("Order outbox table status is updated as: {}", outboxStatus.name());
    }

    private void save(OrderOutboxMessage orderPaymentOutboxMessage) {
        OrderOutboxMessage response = orderOutboxRepository.save(orderPaymentOutboxMessage);
        if (response == null) {
            throw new DriverDomainException("Could not save OrderOutboxMessage!");
        }
        log.info("OrderOutboxMessage saved with id: {}", orderPaymentOutboxMessage.getId());
    }

    private String createPayload(OrderEventPayload orderEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderEventPayload object for order id: {}",
                    orderEventPayload.getOrderId(), e);
            throw new DriverDomainException("Could not create OrderEventPayload object for order id: " +
                    orderEventPayload.getOrderId(), e);
        }
    }

}
