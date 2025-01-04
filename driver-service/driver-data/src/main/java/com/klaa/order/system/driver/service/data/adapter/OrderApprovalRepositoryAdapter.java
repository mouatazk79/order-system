package com.klaa.order.system.driver.service.data.adapter;

import com.klaa.order.system.driver.service.data.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.data.repository.OrderApprovalJpaRepository;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
@AllArgsConstructor
public class OrderApprovalRepositoryAdapter implements OrderApprovalRepository {
    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final DriverDataMapper driverDataMapper;
    @Override
    public Optional<OrderApproval> saveOrderApproval(OrderApproval orderApproval) {
        return Optional.of(driverDataMapper.orderApprovalEntityToOrderApproval( orderApprovalJpaRepository.save(driverDataMapper.orderApprovalToOrderApprovalEntity(orderApproval))));
    }

    @Override
    public Optional<OrderApproval> findOrderId(UUID id) {
        return Optional.of(driverDataMapper.orderApprovalEntityToOrderApproval(orderApprovalJpaRepository.findById(id).get()));
    }
}
