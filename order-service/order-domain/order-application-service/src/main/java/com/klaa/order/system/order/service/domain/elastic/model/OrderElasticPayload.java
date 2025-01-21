package com.klaa.order.system.order.service.domain.elastic.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class OrderElasticPayload {
    @JsonProperty("id")
    private IdValue orderId;

    @JsonProperty("userId")
    private IdValue userId;

    @JsonProperty
    private PositionAddress position;

    @JsonProperty
    private PositionAddress destination;

    @JsonProperty("price")
    private PriceAmount price;

    @JsonProperty("driverId")
    private IdValue driverId;

    @JsonProperty("trackingId")
    private IdValue trackingId;

    @JsonProperty
    private String orderStatus;

    @JsonProperty
    private List<String> failureMessages;

    @Getter
    public static class IdValue {
        private String value;

        @JsonCreator
        public IdValue(@JsonProperty("value") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PriceAmount {
        private BigDecimal amount;
        private boolean greaterThanZero;
    }

    @Getter
    @AllArgsConstructor
    public static class PositionAddress {
        private String positionId;
        private String streetAddress;
        private String zipCode;
        private String city;
    }
}
