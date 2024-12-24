package com.klaa.order.system.domain.exception;

public class DriverDomainException extends DomainException{
    public DriverDomainException(String message) {
        super(message);
    }

    public DriverDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
