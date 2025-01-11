package com.klaa.order.system.order.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.klaa.order.system.order.service")
@EntityScan(basePackages = "com.klaa.order.system.order.service")
@SpringBootApplication(scanBasePackages = "com.klaa.order.system.order.service")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
