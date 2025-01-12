package com.klaa.order.system.order.service.domain.elastic.sheduler;

import com.klaa.order.system.order.service.domain.elastic.OrderElasticHelper;
import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.domain.ports.output.publisher.elastic.OrderElasticMessagePublisher;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderElasticRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class OrderElasticScheduler {
    private final OrderElasticHelper orderElasticHelper;
    private final OrderElasticMessagePublisher orderElasticMessagePublisher;
    private final OrderElasticRepository orderElasticRepository;
    @Transactional
    @Scheduled(fixedRate =2,timeUnit = TimeUnit.DAYS)
    public void processElasticMessages(){
        orderElasticHelper.saveOrderElasticMessages(LocalDateTime.now().minusMinutes(2),LocalDateTime.now());
        Optional<List<OrderElasticMessage>> orderElasticMessages=orderElasticRepository.getAllOrderElasticMessageByStatus(ElasticMessageStatus.PENDING,ElasticMessageStatus.FAILED);
        if (orderElasticMessages.isPresent()&&orderElasticMessages.get().size()>0){
            List<OrderElasticMessage> orderMessages=orderElasticMessages.get();
            orderMessages.forEach(orderElasticMessage -> {
                orderElasticMessagePublisher.publish(orderElasticMessage,this::updateOrderElasticMessageStatus);
            });
            log.info("Fetched {} OrderElasticMessage with ids: {}, sending to elastic message bus!",
                    orderMessages.size(),
                    orderMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            log.info("{} OrderElasticMessage sent to elastic message bus!", orderMessages.size());

        }


    }

    private void updateOrderElasticMessageStatus(OrderElasticMessage orderElasticMessage, ElasticMessageStatus elasticMessageStatus) {
        orderElasticMessage.setElasticMessageStatus(elasticMessageStatus);
        orderElasticHelper.save(orderElasticMessage);
        log.info("OrderElasticMessage is updated with elasticMessageStatus status: {}", elasticMessageStatus.name());
    }
}
