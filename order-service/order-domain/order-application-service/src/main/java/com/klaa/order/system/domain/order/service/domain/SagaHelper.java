package com.klaa.order.system.domain.order.service.domain;

import com.klaa.order.system.domain.order.service.domain.entity.Order;
import com.klaa.order.system.domain.order.service.domain.exception.OrderNotFoundException;
import com.klaa.order.system.domain.order.service.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Slf4j
@Component
@AllArgsConstructor
public class SagaHelper {
    private final OrderRepository orderRepository;
    public Order findOrderById(UUID id){
        Optional<Order> order=orderRepository.findOrderById(id);
        if (order.isEmpty()){
            log.info("order with id: {} does not exist",id);
            throw new OrderNotFoundException("order with id:"+id+"does not exist");
        }
        return order.get();
    }
    public void saveOrder(Order order){
        orderRepository.saveOrder(order);
    }



}
