package com.klaa.order.system.order.service.data.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(schema = "\"order\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID positionId;
    private String streetAddress;
    private String zipCode;
    private String city;

}
