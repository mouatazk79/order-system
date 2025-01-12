package com.klaa.order.system.order.service.data.order.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(schema = "\"order\"")
@Data
public class PositionAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID positionId;
    private String streetAddress;
    private String zipCode;
    private String city;

}
