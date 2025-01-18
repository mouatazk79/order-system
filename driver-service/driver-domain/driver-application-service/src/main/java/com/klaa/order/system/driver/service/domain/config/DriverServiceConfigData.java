package com.klaa.order.system.driver.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@Configuration
@EnableScheduling
@ConfigurationProperties(prefix = "driver-service")
public class DriverServiceConfigData {
    private String driverApprovalRequestTopicName;
    private String driverApprovalResponseTopicName;
}
