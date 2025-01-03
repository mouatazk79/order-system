package com.klaa.order.system.order.service.data.outbox.driver.exception;

public class DriverOutboxDataException extends RuntimeException{
    public DriverOutboxDataException(String message) {
        super(message);
    }

    public DriverOutboxDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
