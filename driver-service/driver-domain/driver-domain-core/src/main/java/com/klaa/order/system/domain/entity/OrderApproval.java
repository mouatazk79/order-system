package com.klaa.order.system.domain.entity;

import com.klaa.order.system.domain.valueobject.OrderApprovalId;
import com.klaa.order.system.domain.valueobjects.DriverId;
import com.klaa.order.system.domain.valueobjects.DriverOrderStatus;
import com.klaa.order.system.domain.valueobjects.OrderStatus;

import java.util.List;

public class OrderApproval extends BaseEntity<OrderApprovalId>{
    private final DriverId driverId;
    private final OrderDetail orderDetail;
    private DriverOrderStatus orderStatus;


   public void validateOrder(List<String> failureMessages){
        if (orderDetail.getOrderStatus()!= OrderStatus.PENDING){
            failureMessages.add("order is not in pending state");
        }
    }
   public void changeStatus(DriverOrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }

    private OrderApproval(Builder builder) {
        setId(builder.orderApprovalId);
        this.driverId = builder.driverId;
        this.orderDetail = builder.orderDetail;
        this.orderStatus = builder.orderStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OrderApprovalId orderApprovalId;
        private DriverId driverId;
        private OrderDetail orderDetail;
        private DriverOrderStatus orderStatus;

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

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public DriverOrderStatus getOrderStatus() {
        return orderStatus;
    }
}
