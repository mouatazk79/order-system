package com.klaa.order.system.order.service.domain.elastic.sheduler;

import com.klaa.order.system.order.service.domain.elastic.OrderElasticHelper;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.domain.ports.output.publisher.elastic.OrderElasticMessagePublisher;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderElasticRepository;
import com.klaa.order.system.outbox.OutboxStatus;
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
    @Scheduled(fixedRate =2,timeUnit = TimeUnit.MINUTES)
    public void processElasticMessages(){
        orderElasticHelper.saveOrderElasticMessages(LocalDateTime.now().minusMinutes(2),LocalDateTime.now());
        Optional<List<OrderElasticMessage>> orderElasticMessages=orderElasticRepository.getAllOrderElasticMessageByOutboxStatuses(OutboxStatus.STARTED);
        if (orderElasticMessages.isPresent()&&orderElasticMessages.get().size()>0){
            List<OrderElasticMessage> orderMessages=orderElasticMessages.get();
            log.info("Fetched {} OrderElasticMessage with ids: {}, sending to elastic message bus!",
                    orderMessages.size(),
                    orderMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            orderMessages.forEach(orderElasticMessage -> orderElasticMessagePublisher.publish(orderElasticMessage,this::updateOutboxStatus));
            log.info("{} OrderElasticMessage sent to elastic message bus!", orderMessages.size());

        }


    }

    private void updateOutboxStatus(OrderElasticMessage orderElasticMessage, OutboxStatus outboxStatus) {
        orderElasticMessage.setOutboxStatus(outboxStatus);
        orderElasticHelper.save(orderElasticMessage);
        log.info("OrderElasticMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
