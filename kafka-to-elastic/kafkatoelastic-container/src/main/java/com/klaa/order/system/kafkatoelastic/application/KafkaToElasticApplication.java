package com.klaa.order.system.kafkatoelastic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.klaa.order.system")
@SpringBootApplication(scanBasePackages = "com.klaa.order.system.kafkatoelastic")
public class KafkaToElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticApplication.class,args);
    }
}
