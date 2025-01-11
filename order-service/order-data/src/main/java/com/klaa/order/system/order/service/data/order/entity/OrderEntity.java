package com.klaa.order.system.order.service.data.order.entity;

import com.klaa.order.system.data.driver.common.BaseJpaEntity;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity extends BaseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;
    private UUID userId;
    @OneToOne(cascade = CascadeType.ALL)
    private PositionAddress position;
    @OneToOne(cascade = CascadeType.ALL)
    private PositionAddress destination;
    private BigDecimal price;
    private UUID driverId;
    private UUID trackingId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String failureMessages;




}
