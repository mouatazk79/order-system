package com.klaa.order.system.driver.service.domain.exception;

import com.klaa.order.system.domain.exception.DomainException;

public class DriverNotFoundException extends DomainException {
    public DriverNotFoundException(String message) {
        super(message);
    }

    public DriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
