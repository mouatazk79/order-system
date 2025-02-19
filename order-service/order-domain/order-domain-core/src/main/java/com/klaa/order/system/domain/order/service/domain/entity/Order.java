package com.klaa.order.system.domain.order.service.domain.entity;

import com.klaa.order.system.domain.entity.AggregateRoot;
import com.klaa.order.system.domain.order.service.domain.exception.OrderDomainException;
import com.klaa.order.system.domain.order.service.domain.valueobjects.TrackingId;
import com.klaa.order.system.domain.valueobjects.*;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
private final UserId userId;
private final Position position;
private final Position destination;
private final Money price;
private DriverId driverId;
private TrackingId trackingId;
private OrderStatus orderStatus;
private List<String> failureMessages;

    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    public void initializeOrder(DriverId driverId) {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        this.driverId=driverId;
        orderStatus = OrderStatus.PENDING;
    }

    public void validateOrder() {
        validateInitialOrder();
        validateDestination();
    }
    public void initReject(List<String> failureMessages){
        if(!(orderStatus ==OrderStatus.PENDING || orderStatus ==OrderStatus.APPROVED)){
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus=OrderStatus.REJECTING;
        updateFailureMessages(failureMessages);
    }

    public void reject(List<String> failureMessages){
        if(!(orderStatus ==OrderStatus.REJECTING || orderStatus ==OrderStatus.PENDING)){
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus=OrderStatus.REJECTED;
        updateFailureMessages(failureMessages);
    }

    public void pay() {
        if (orderStatus != OrderStatus.APPROVED) {
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if(orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in correct state for approve operation!");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not in correct state for initCancel operation!");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
            throw new OrderDomainException("Order is not in correct state for cancel operation!");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }
    }

    private void validateDestination() {
        if (position.equals(destination)) {
            throw new OrderDomainException("current position and destination are the same");
        }
    }



    private Order(Builder builder) {
        super.setId(builder.orderId);
        userId = builder.userId;
        driverId = builder.driverId;
        position = builder.position;
        destination=builder.destination;
        price = builder.price;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UserId getUserId() {
        return userId;
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public Position getPosition() {
        return position;
    }

    public Position getDestination() {
        return destination;
    }

    public Money getPrice() {
        return price;
    }


    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private UserId userId;
        private DriverId driverId;
        private Position position;
        private Position destination;
        private Money price;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder driverId(DriverId val) {
            driverId = val;
            return this;
        }

        public Builder position(Position val) {
            position = val;
            return this;
        }
        public Builder destination(Position val) {
            destination= val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }


        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
