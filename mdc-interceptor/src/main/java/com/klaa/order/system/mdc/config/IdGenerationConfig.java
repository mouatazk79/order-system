package com.klaa.order.system.mdc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

@Configuration
public class IdGenerationConfig {
    @Bean
    public IdGenerator idGenerator(){
        return new JdkIdGenerator();
    }
}
