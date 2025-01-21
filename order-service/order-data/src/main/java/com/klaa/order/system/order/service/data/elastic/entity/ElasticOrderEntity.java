package com.klaa.order.system.order.service.data.elastic.entity;


import com.klaa.order.system.outbox.OutboxStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Table(schema = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElasticOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(columnDefinition="text", length=10485760)
    private String payload;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;

}
