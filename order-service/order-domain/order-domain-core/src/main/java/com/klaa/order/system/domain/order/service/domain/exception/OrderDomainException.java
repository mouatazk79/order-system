package com.klaa.order.system.domain.order.service.domain.exception;

import com.klaa.order.system.domain.exception.DomainException;

public class OrderDomainException extends DomainException {


    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
