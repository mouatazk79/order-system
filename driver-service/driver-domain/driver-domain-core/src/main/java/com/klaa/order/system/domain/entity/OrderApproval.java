package com.klaa.order.system.domain.entity;

import com.klaa.order.system.domain.valueobject.OrderApprovalId;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.OrderId;

public class OrderApproval extends BaseEntity<OrderApprovalId>{
    private final DriverId driverId;
    private final OrderId orderId;
    private final DriverOrderStatus orderStatus;
    private OrderApproval(Builder builder) {
        setId(builder.orderApprovalId);
        this.driverId = builder.driverId;
        this.orderId = builder.orderId;
        this.orderStatus = builder.orderStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OrderApprovalId orderApprovalId;
        private DriverId driverId;
        private OrderId orderId;
        private DriverOrderStatus orderStatus;

        public Builder driverId(OrderApprovalId orderApprovalId) {
            this.orderApprovalId = orderApprovalId;
            return this;
        }
        public Builder driverId(DriverId driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder orderStatus(DriverOrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public DriverOrderStatus getOrderStatus() {
        return orderStatus;
    }
}
