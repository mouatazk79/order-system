package com.klaa.order.system.driver.service.application.exception;

import com.klaa.order.system.application.ErrorDTO;
import com.klaa.order.system.driver.service.domain.exception.DriverDomainException;
import com.klaa.order.system.driver.service.domain.exception.DriverNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class DriverExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {DriverDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(DriverDomainException driverDomainException) {
        log.error(driverDomainException.getMessage(), driverDomainException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(driverDomainException.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {DriverNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(DriverNotFoundException driverNotFoundException) {
        log.error(driverNotFoundException.getMessage(), driverNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(driverNotFoundException.getMessage())
                .build();
    }
}
