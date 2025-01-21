package com.klaa.order.system.order.service.domain.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;

import com.klaa.order.system.order.service.domain.ports.output.repository.OrderElasticRepository;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderElasticHelper {
    private final OrderElasticRepository orderElasticRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public void saveOrderElasticMessages(LocalDateTime start,LocalDateTime end){
        List<Order> orders=orderRepository.findOrdersCreatedAtBetween(start,end);
         orders.forEach(order -> {
            OrderElasticMessage orderElasticMessage=OrderElasticMessage.builder()
                    .id(UUID.randomUUID())
                    .payload(createPayload(order))
                    .outboxStatus(OutboxStatus.STARTED)
                    .build();
            orderElasticRepository.save(orderElasticMessage);
        });
    }

    public void save(OrderElasticMessage orderElasticMessage){
        orderElasticRepository.save(orderElasticMessage);
    }

    private String createPayload(Order order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            log.error("Could not create Order payload object for order id: {}",
                    order.getId(), e);
            throw new OrderDomainException("Could not create Order payload object for order id: " +
                    order.getId(), e);
        }
    }


    public void deleteOrderElasticMessageById(UUID id) {
        orderElasticRepository.deleteOrderElasticMessageById(id);
    }
}
