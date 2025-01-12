package com.klaa.order.system.payment.service.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.klaa.order.system.payment.service.data",})
@EntityScan(basePackages = {"com.klaa.order.system.payment.service.data","com.klaa.order.system.data"})
@SpringBootApplication(scanBasePackages = "com.klaa.order.system")
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class,args);
    }
}
