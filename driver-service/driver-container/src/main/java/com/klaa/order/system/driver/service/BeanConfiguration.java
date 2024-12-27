package com.klaa.order.system.driver.service;

import com.klaa.order.system.domain.DriverDomainService;
import com.klaa.order.system.domain.DriverDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DriverDomainService driverDomainService(){
        return new DriverDomainServiceImpl();
    }
}
