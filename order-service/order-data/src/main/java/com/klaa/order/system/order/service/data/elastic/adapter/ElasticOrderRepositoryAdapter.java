package com.klaa.order.system.order.service.data.elastic.adapter;

import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderElasticRepository;
import com.klaa.order.system.order.service.data.elastic.mapper.ElasticOrderDataMapper;
import com.klaa.order.system.order.service.data.elastic.repository.ElasticOrderJpaRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@AllArgsConstructor
public class ElasticOrderRepositoryAdapter implements OrderElasticRepository {
    private final ElasticOrderJpaRepository elasticOrderJpaRepository;
    private final ElasticOrderDataMapper elasticOrderDataMapper;
    @Override
    public Optional<OrderElasticMessage> save(OrderElasticMessage orderElasticMessage) {
        return Optional.of(elasticOrderDataMapper.elasticOrderEntityToOrderElasticMessage( elasticOrderJpaRepository.save(elasticOrderDataMapper.orderElasticMessageToElasticOrderEntity(orderElasticMessage))));
    }

    @Override
    public Optional<List<OrderElasticMessage>> getAllOrderElasticMessageByOutboxStatuses(OutboxStatus... outboxStatuses) {
        return Optional.of(elasticOrderJpaRepository
                .findByOutboxStatusIn(Arrays.asList(outboxStatuses))
                .get()
                .stream().map(elasticOrderDataMapper::elasticOrderEntityToOrderElasticMessage)
                .collect(Collectors.toList()));

    }

    @Override
    public void deleteOrderElasticMessageById(UUID id) {
        elasticOrderJpaRepository.deleteById(id);
    }
}
