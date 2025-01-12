package com.klaa.order.system.data.driver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Table(schema = "\"order\"")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String car;
    private String phoneNumber;
    private Boolean active;
}
