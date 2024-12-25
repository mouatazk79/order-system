package com.klaa.order.system.domain;

import com.klaa.order.system.domain.dto.track.TrackOrderQuery;
import com.klaa.order.system.domain.dto.track.TrackOrderResponse;
import com.klaa.order.system.domain.entity.Order;
import com.klaa.order.system.domain.exception.OrderNotFoundException;
import com.klaa.order.system.domain.mapper.OrderDataMapper;
import com.klaa.order.system.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Component
@AllArgsConstructor
@Slf4j
public class OrderTrackCommandHandler {
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order=orderRepository.findOrderById(trackOrderQuery.getOrderTrackingId());
        if (order.isEmpty()){
            throw new OrderNotFoundException("order with id: "+trackOrderQuery.getOrderTrackingId()+"does not exist");
        }
        return orderDataMapper.orderToTrackOrderResponse(trackOrderQuery);

    }

    }
