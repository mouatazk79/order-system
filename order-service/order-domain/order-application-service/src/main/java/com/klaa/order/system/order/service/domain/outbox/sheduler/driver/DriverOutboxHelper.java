package com.klaa.order.system.order.service.domain.outbox.sheduler.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.order.service.domain.outbox.model.driver.DriverRequestPayload;
import com.klaa.order.system.order.service.domain.ports.output.repository.DriverOutboxRepository;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class DriverOutboxHelper {
    private final DriverOutboxRepository driverOutboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void save(DriverRequestOutboxMessage driverRequestOutboxMessage) {
        Optional<DriverRequestOutboxMessage> newDriverRequestOutboxMessage = driverOutboxRepository.save(driverRequestOutboxMessage);
        if (newDriverRequestOutboxMessage.isEmpty()) {
            log.error("Could not save DriverRequestOutboxMessage with outbox id: {}", newDriverRequestOutboxMessage.get().getId());
            throw new OrderDomainException("Could not save DriverRequestOutboxMessage with outbox id: " +
                    driverRequestOutboxMessage.getId());
        }
        log.info("DriverRequestOutboxMessage saved with outbox id: {}", driverRequestOutboxMessage.getId());
    }

    @Transactional(readOnly = true)
    public Optional<List<DriverRequestOutboxMessage>> getDriverRequestOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return driverOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus("OrderProcessingSaga",
                outboxStatus,
                sagaStatus);
    }
    @Transactional(readOnly = true)
    public Optional<DriverRequestOutboxMessage> getDriverRequestOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                            SagaStatus... sagaStatus) {
        return driverOutboxRepository.findByTypeAndSagaIdAndSagaStatus("OrderProcessingSaga", sagaId, sagaStatus);
    }
    @Transactional
    public void saveDriverRequestOutboxMessage(DriverRequestPayload driverRequestPayload,
                                         OrderStatus orderStatus,
                                         SagaStatus sagaStatus,
                                         OutboxStatus outboxStatus,
                                         UUID sagaId) {
        save(DriverRequestOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(driverRequestPayload.getCreatedAt())
                .type("OrderProcessingSaga")
                .payload(createPayload(driverRequestPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build());
    }
    @Transactional
    public void deleteDriverOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatus) {
        driverOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus("OrderProcessingSaga", outboxStatus, sagaStatus);
    }




    private String createPayload(DriverRequestPayload driverRequestPayload) {
        try {
            return objectMapper.writeValueAsString(driverRequestPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload object for order id: {}",
                    driverRequestPayload.getOrderId(), e);
            throw new OrderDomainException("Could not create OrderPaymentEventPayload object for order id: " +
                    driverRequestPayload.getOrderId(), e);
        }
    }



}
