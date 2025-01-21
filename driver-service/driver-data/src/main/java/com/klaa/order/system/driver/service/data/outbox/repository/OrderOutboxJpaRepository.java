package com.klaa.order.system.driver.service.data.outbox.repository;


import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.driver.service.data.outbox.entity.OrderOutboxEntity;
import com.klaa.order.system.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderOutboxJpaRepository extends JpaRepository<OrderOutboxEntity, UUID> {

    Optional<List<OrderOutboxEntity>> findByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<OrderOutboxEntity> findByTypeAndSagaIdAndOutboxStatus(String type, UUID sagaId, OutboxStatus outboxStatus);

    Optional<OrderOutboxEntity> findByOrderIdAndOutboxStatus(UUID orderId, OutboxStatus outboxStatus);


    void deleteByTypeAndOutboxStatus(String type, OutboxStatus outboxStatus);

    Optional<List<OrderOutboxEntity>> findByDriverOrderStatusNotAndOutboxStatus(DriverOrderStatus driverOrderStatus, OutboxStatus outboxStatus);

}
