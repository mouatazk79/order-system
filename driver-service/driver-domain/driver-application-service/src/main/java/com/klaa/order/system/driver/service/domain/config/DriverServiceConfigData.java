package com.klaa.order.system.driver.service.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "restaurant-service")
public class DriverServiceConfigData {
    private String driverApprovalRequestTopicName;
    private String driverApprovalResponseTopicName;
}
