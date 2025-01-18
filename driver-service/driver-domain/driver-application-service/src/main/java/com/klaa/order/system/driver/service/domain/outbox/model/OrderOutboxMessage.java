package com.klaa.order.system.driver.service.domain.outbox.model;


import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderOutboxMessage {
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String type;
    private String payload;
    private OutboxStatus outboxStatus;
    private DriverOrderStatus driverOrderStatus;
    private int version;

    public void setOutboxStatus(OutboxStatus status) {
        this.outboxStatus = status;
    }
    public void setDriverOrderStatus(DriverOrderStatus status) {
        this.driverOrderStatus = status;
    }

    @Override
    public String toString() {
        return "OrderOutboxMessage{" +
                "id=" + id +
                ", sagaId=" + sagaId +
                ", createdAt=" + createdAt +
                ", processedAt=" + processedAt +
                ", type='" + type + '\'' +
                ", payload='" + payload + '\'' +
                ", outboxStatus=" + outboxStatus +
                ", driverOrderStatus=" + driverOrderStatus +
                ", version=" + version +
                '}';
    }
}
