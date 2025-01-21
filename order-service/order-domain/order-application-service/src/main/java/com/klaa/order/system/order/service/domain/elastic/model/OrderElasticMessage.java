package com.klaa.order.system.order.service.domain.elastic.model;

import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;
@Builder
@Data
@AllArgsConstructor
public class OrderElasticMessage {
    private UUID id;
    private String payload;
    private OutboxStatus outboxStatus;

}
