package com.klaa.order.system.kafkatoelastic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "com.klaa.order.system")
@ComponentScan(basePackages = {
        "com.klaa.order.system",
        "com.klaa.order.system.elastic.config",
        "com.klaa.order.system.elastic.indexclient",
        "com.klaa.order.system.elastic.model"
})
public class KafkaToElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticApplication.class,args);
    }
}
