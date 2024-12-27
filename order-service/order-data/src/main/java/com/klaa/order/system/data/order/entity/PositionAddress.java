package com.klaa.order.system.data.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class PositionAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID positionId;
    private String streetAddress;
    private String zipCode;
    private String city;

}
