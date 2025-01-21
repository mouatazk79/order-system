package com.klaa.order.system.order.service.data.order.repository;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.order.service.data.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByCreatedDateIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    Optional<OrderEntity> findByTrackingId(UUID trackingId);
    Optional<OrderEntity> findByOrderId(UUID orderId);

}
