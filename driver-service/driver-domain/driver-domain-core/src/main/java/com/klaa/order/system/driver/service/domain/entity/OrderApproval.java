package com.klaa.order.system.driver.service.domain.entity;

import com.klaa.order.system.domain.entity.BaseEntity;
import com.klaa.order.system.driver.service.domain.valueobject.OrderApprovalId;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.OrderStatus;

import java.util.List;

public class OrderApproval extends BaseEntity<OrderApprovalId> {
    private final DriverId driverId;
    private final OrderDetail orderDetail;
    private DriverOrderStatus driverOrderStatus;


   public void validateOrder(List<String> failureMessages){
        if (orderDetail.getOrderStatus()!= OrderStatus.PENDING){
            failureMessages.add("order is not in pending state");
        }
    }
   public void changeStatus(DriverOrderStatus driverOrderStatus){
        this.driverOrderStatus=driverOrderStatus;
    }

    private OrderApproval(Builder builder) {
        setId(builder.orderApprovalId);
        this.driverId = builder.driverId;
        this.orderDetail = builder.orderDetail;
        this.driverOrderStatus = builder.driverOrderStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OrderApprovalId orderApprovalId;
        private DriverId driverId;
        private OrderDetail orderDetail;
        private DriverOrderStatus driverOrderStatus;

        public Builder driverId(OrderApprovalId orderApprovalId) {
            this.orderApprovalId = orderApprovalId;
            return this;
        }
        public Builder driverId(DriverId driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder orderDetail(OrderDetail orderDetail) {
            this.orderDetail = orderDetail;
            return this;
        }

        public Builder driverOrderStatus(DriverOrderStatus driverOrderStatus) {
            this.driverOrderStatus = driverOrderStatus;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public DriverOrderStatus getOrderStatus() {
        return driverOrderStatus;
    }
}
