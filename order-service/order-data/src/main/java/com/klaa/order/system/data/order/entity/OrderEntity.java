package com.klaa.order.system.data.order.entity;

import com.klaa.order.system.domain.valueobjects.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orderId;
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
