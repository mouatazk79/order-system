package com.klaa.order.system.order.service.data.elastic.repository;

import com.klaa.order.system.order.service.data.elastic.entity.ElasticOrderEntity;
import com.klaa.order.system.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ElasticOrderJpaRepository extends JpaRepository<ElasticOrderEntity, UUID> {
   Optional<List<ElasticOrderEntity>> findByOutboxStatusIn(List<OutboxStatus> elasticMessageStatuses);
}
