package com.klaa.order.system.driver.service.data.outbox.entity;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.outbox.OutboxStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderOutboxEntity {

    @Id
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String type;
    private String payload;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Enumerated(EnumType.STRING)
    private DriverOrderStatus driverOrderStatus;
    private int version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderOutboxEntity that = (OrderOutboxEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "OrderOutboxEntity{" +
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

