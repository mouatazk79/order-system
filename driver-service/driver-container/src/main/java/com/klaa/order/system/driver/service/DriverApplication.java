package com.klaa.order.system.driver.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.klaa.order.system")
@EntityScan(basePackages = "com.klaa.order.system")
@SpringBootApplication(scanBasePackages = "com.klaa.order.system.driver.service")
public class DriverApplication {
    public static void main(String[] args) {
        SpringApplication.run(DriverApplication.class,args);
    }
}
