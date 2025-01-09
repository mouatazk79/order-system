package com.klaa.order.system.domain.order.service.domain.elastic.model;

import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
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
    private ElasticMessageStatus elasticMessageStatus=ElasticMessageStatus.PENDING;

}
