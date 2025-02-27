package com.klaa.order.system.order.service.domain.elastic.sheduler;

import com.klaa.order.system.order.service.domain.elastic.OrderElasticHelper;
import com.klaa.order.system.order.service.domain.elastic.model.OrderElasticMessage;
import com.klaa.order.system.order.service.domain.ports.output.repository.OrderElasticRepository;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class OrderElasticSchedulerCleaner {
    private final OrderElasticHelper orderElasticHelper;
    private final OrderElasticRepository orderElasticRepository;

    @Transactional
    @Scheduled(fixedRate =12 ,timeUnit = TimeUnit.HOURS)
    public void processElasticMessages(){
        Optional<List<OrderElasticMessage>> orderElasticMessages=orderElasticRepository.getAllOrderElasticMessageByOutboxStatuses(OutboxStatus.COMPLETED);
        if (orderElasticMessages.isPresent()&&orderElasticMessages.get().size()>0){
            List<OrderElasticMessage> orderMessages=orderElasticMessages.get();
            orderMessages.forEach(orderElasticMessage -> {
                orderElasticHelper.deleteOrderElasticMessageById(orderElasticMessage.getId());
            });
        }




    }




}
