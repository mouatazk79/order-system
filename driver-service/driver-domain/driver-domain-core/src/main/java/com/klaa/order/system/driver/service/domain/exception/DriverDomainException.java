package com.klaa.order.system.driver.service.domain.exception;

import com.klaa.order.system.domain.exception.DomainException;

public class DriverDomainException extends DomainException {
    public DriverDomainException(String message) {
        super(message);
    }

    public DriverDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
