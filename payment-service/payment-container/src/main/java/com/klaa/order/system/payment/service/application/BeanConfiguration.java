package com.klaa.order.system.payment.service.application;

import com.klaa.order.service.payment.service.domain.PaymentDomainService;
import com.klaa.order.service.payment.service.domain.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public PaymentDomainService orderDomainService(){
        return new PaymentDomainServiceImpl();
    }
}
