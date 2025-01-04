package com.klaa.order.system.driver.service.data.entity;

import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderApprovalId;
    private UUID driverId;
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private DriverOrderStatus status;
}
