package com.klaa.order.system.driver.service.data.adapter;

import com.klaa.order.system.driver.service.data.entity.OrderApprovalEntity;
import com.klaa.order.system.driver.service.data.mapper.DriverDataMapper;
import com.klaa.order.system.driver.service.data.repository.OrderApprovalJpaRepository;
import com.klaa.order.system.driver.service.domain.entity.OrderApproval;
import com.klaa.order.system.driver.service.domain.ports.output.repository.OrderApprovalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Slf4j
@Component
@AllArgsConstructor
public class OrderApprovalRepositoryAdapter implements OrderApprovalRepository {
    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final DriverDataMapper driverDataMapper;
    @Override
    public Optional<OrderApproval> saveOrderApproval(OrderApproval orderApproval) {
        log.info("OrderApproval............ : {}",orderApproval);
      OrderApprovalEntity orderApprovalEntity= orderApprovalJpaRepository.save(driverDataMapper.orderApprovalToOrderApprovalEntity(orderApproval));
      log.info("orderApprovalEntity............... : {}",orderApprovalEntity);
        OrderApproval orderApproval1=  driverDataMapper.orderApprovalEntityToOrderApproval(orderApprovalEntity);
        log.info("OrderApproval ========= : {}",orderApproval1);
        return Optional.of(orderApproval1);
    }

    @Override
    public Optional<OrderApproval> findByOrderId(UUID id) {
        return Optional.of(driverDataMapper.orderApprovalEntityToOrderApproval(orderApprovalJpaRepository.findByOrderId(id)));
    }
}
