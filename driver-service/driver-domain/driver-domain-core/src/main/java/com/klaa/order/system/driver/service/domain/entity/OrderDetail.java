package com.klaa.order.system.driver.service.domain.entity;

import com.klaa.order.system.domain.entity.AggregateRoot;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.OrderId;
import com.klaa.order.system.domain.valueobjects.OrderStatus;
import com.klaa.order.system.domain.valueobjects.Position;


public class OrderDetail extends AggregateRoot<OrderId> {
    private final Position position;
    private final Position destination;
    private  Money price;
    private OrderStatus orderStatus;
    private OrderDetail(Builder builder) {
        setId(builder.orderId);
        orderStatus = builder.orderStatus;
        position = builder.position;
        destination = builder.destination;
        price=builder.price;
        orderStatus=builder.orderStatus;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public static Builder builder() {
        return new OrderDetail.Builder();
    }


    public static final class Builder{
        private OrderId orderId;
        private Position position;
        private Position destination;
        private Money price;
        private OrderStatus orderStatus;

        private Builder() {
        }

        public Builder orderId(OrderId val){
            orderId=val;
            return this;
        }
        public Builder position(Position val){
            position=val;
            return this;
        }
        public Builder destination(Position val){
            destination=val;
            return this;
        }
        public Builder price(Money val){
            price=val;
            return this;
        }
        public Builder price(OrderStatus val){
            orderStatus=val;
            return this;
        }
        public OrderDetail build() {
            return new OrderDetail(this);
        }


    }
}
