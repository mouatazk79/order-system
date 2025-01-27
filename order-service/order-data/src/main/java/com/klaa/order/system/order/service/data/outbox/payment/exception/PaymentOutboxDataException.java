package com.klaa.order.system.order.service.data.outbox.payment.exception;

public class PaymentOutboxDataException extends RuntimeException{
    public PaymentOutboxDataException(String message) {
        super(message);
    }

}
