package com.klaa.order.service.payment.service.domain;


import com.klaa.order.service.payment.service.domain.entity.CreditEntry;
import com.klaa.order.service.payment.service.domain.entity.CreditHistory;
import com.klaa.order.service.payment.service.domain.entity.Payment;
import com.klaa.order.service.payment.service.domain.event.PaymentCancelledEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentCompletedEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentEvent;
import com.klaa.order.service.payment.service.domain.event.PaymentFailedEvent;
import com.klaa.order.service.payment.service.domain.valueobject.CreditHistoryId;
import com.klaa.order.service.payment.service.domain.valueobject.TransactionType;
import com.klaa.order.system.domain.valueobjects.Money;
import com.klaa.order.system.domain.valueobjects.PaymentStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {

    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment,
                                                   CreditEntry creditEntry,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        if (failureMessages.isEmpty()) {
            log.info("Payment is initiated for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
        } else {
            log.info("Payment initiation is failed for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
        }
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);

       if (failureMessages.isEmpty()) {
           log.info("Payment is cancelled for order id: {}", payment.getOrderId().getValue());
           payment.updateStatus(PaymentStatus.CANCELLED);
           return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")));
       } else {
           log.info("Payment cancellation is failed for order id: {}", payment.getOrderId().getValue());
           payment.updateStatus(PaymentStatus.FAILED);
           return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of("UTC")), failureMessages);
       }
    }

    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer with id: {} doesn't have enough credit for payment!",
                    payment.getUserId().getValue());
            failureMessages.add("Customer with id=" + payment.getUserId().getValue()
                    + " doesn't have enough credit for payment!");
        }
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(Payment payment,
                                     List<CreditHistory> creditHistories,
                                     TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .userId(payment.getUserId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }


    private void validateCreditHistory(CreditEntry creditEntry,
                                       List<CreditHistory> creditHistories,
                                       List<String> failureMessages) {
            Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
            Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

            if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
                log.error("Customer with id: {} doesn't have enough credit according to credit history",
                        creditEntry.getUserId().getValue());
                failureMessages.add("Customer with id=" + creditEntry.getUserId().getValue() +
                        " doesn't have enough credit according to credit history!");
            }

            if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
                log.error("Credit history total is not equal to current credit for customer id: {}!",
                        creditEntry.getUserId().getValue());
                failureMessages.add("Credit history total is not equal to current credit for customer id: " +
                        creditEntry.getUserId().getValue() + "!");
            }
    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAmount(payment.getPrice());
    }
}
