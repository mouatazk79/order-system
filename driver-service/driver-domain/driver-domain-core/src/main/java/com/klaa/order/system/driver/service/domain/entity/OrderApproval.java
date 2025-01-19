package com.klaa.order.system.driver.service.domain.entity;

import com.klaa.order.system.domain.entity.BaseEntity;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.driver.service.domain.valueobject.OrderApprovalId;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;

import java.util.List;

public class OrderApproval extends BaseEntity<OrderApprovalId> {
    private final DriverId driverId;
    private DriverOrderStatus driverOrderStatus;
    private final OrderId orderId;


    public void validateOrder(List<String> failureMessages){
        if (driverOrderStatus!=DriverOrderStatus.PENDING){
            failureMessages.add("order is not in pending state");
        }
    }

   public void changeStatus(DriverOrderStatus driverOrderStatus){
        this.driverOrderStatus=driverOrderStatus;
    }

    private OrderApproval(Builder builder) {
        setId(builder.orderApprovalId);
        this.driverId = builder.driverId;
        this.driverOrderStatus = builder.driverOrderStatus;
        this.orderId=builder.orderId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OrderApprovalId orderApprovalId;
        private DriverId driverId;
        private DriverOrderStatus driverOrderStatus;
        private OrderId orderId;

        public Builder orderApprovalId(OrderApprovalId orderApprovalId) {
            this.orderApprovalId = orderApprovalId;
            return this;
        }
        public Builder driverId(DriverId driverId) {
            this.driverId = driverId;
            return this;
        }


        public Builder driverOrderStatus(DriverOrderStatus driverOrderStatus) {
            this.driverOrderStatus = driverOrderStatus;
            return this;
        }

        public Builder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }

    public DriverId getDriverId() {
        return driverId;
    }


    public DriverOrderStatus getDriverOrderStatus() {
        return driverOrderStatus;
    }



    public OrderId getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderApproval{" +
                "driverId=" + driverId +
                ", driverOrderStatus=" + driverOrderStatus +
                '}';
    }
}
