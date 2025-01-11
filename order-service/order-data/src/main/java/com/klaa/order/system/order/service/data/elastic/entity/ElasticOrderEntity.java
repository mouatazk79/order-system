package com.klaa.order.system.order.service.data.elastic.entity;

import com.klaa.order.system.domain.valueobjects.ElasticMessageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String payload;
    @Enumerated(EnumType.STRING)
    private ElasticMessageStatus elasticMessageStatus;
}
