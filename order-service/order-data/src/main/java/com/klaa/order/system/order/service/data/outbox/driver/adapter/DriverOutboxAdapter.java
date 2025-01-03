package com.klaa.order.system.order.service.data.outbox.driver.adapter;

import com.klaa.order.system.domain.order.service.domain.outbox.model.driver.DriverRequestOutboxMessage;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.DriverOutboxRepository;
import com.klaa.order.system.order.service.data.outbox.driver.exception.DriverOutboxDataException;
import com.klaa.order.system.order.service.data.outbox.driver.mapper.DriverOutboxDataMapper;
import com.klaa.order.system.order.service.data.outbox.driver.repository.DriverOutboxJpaRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import com.klaa.order.system.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class DriverOutboxAdapter implements DriverOutboxRepository {
    private final DriverOutboxJpaRepository driverOutboxJpaRepository;
    private final DriverOutboxDataMapper driverOutboxDataMapper;
    @Override
    public Optional<DriverRequestOutboxMessage> save(DriverRequestOutboxMessage driverRequestOutboxMessage) {
        return driverOutboxDataMapper
                .approvalOutboxEntityToOrderApprovalOutboxMessage(driverOutboxJpaRepository
                        .save(driverOutboxDataMapper
                                .driverOutboxMessageToOutDriverOutboxMessageEntity(driverRequestOutboxMessage)));
    }

    @Override
    public Optional<List<DriverRequestOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        return Optional.of(driverOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus,
                        Arrays.asList(sagaStatus))
                .orElseThrow(() -> new DriverOutboxDataException("Approval outbox object " +
                        "could be found for saga type " + type))
                .stream()
                .map(driverOutboxDataMapper::driverOutboxMessageEntityToDriverOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<DriverRequestOutboxMessage> findByTypeAndSagaIdAndSagaStatus(String type, UUID sagaId, SagaStatus... sagaStatus) {

        return driverOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId,
                        Arrays.asList(sagaStatus))
                .map(driverOutboxDataMapper::driverOutboxMessageEntityToDriverOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        driverOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus,
                Arrays.asList(sagaStatus));

    }
}
