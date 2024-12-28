package com.klaa.order.system.order.service.application;

import com.klaa.order.system.domain.order.service.domain.OrderDomainService;
import com.klaa.order.system.domain.order.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
