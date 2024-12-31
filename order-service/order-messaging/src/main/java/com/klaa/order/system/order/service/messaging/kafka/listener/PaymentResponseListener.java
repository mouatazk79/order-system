package com.klaa.order.system.order.service.messaging.kafka.listener;

import com.klaa.order.system.domain.order.service.domain.ports.input.listener.payment.PaymentResponseMessageListener;
import com.klaa.order.system.kafka.consumer.consumer.KafkaConsumer;
import com.klaa.order.system.kafka.model.payment.PaymentResponseAvroModel;
import com.klaa.order.system.kafka.model.payment.PaymentStatus;
import com.klaa.order.system.order.service.messaging.kafka.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class PaymentResponseListener implements KafkaConsumer<PaymentResponseAvroModel> {
    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PaymentResponseListener(PaymentResponseMessageListener paymentResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.paymentResponseMessageListener = paymentResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }


    @Override
    public void receive(@Payload List<PaymentResponseAvroModel> messages, @Header(KafkaHeaders.KEY) List<String> keys,@Header(KafkaHeaders.PARTITION) List<Integer> partitions,@Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(
                message -> {
                    PaymentStatus messagePaymentOrderStatus=message.getPaymentStatus();
                    if (PaymentStatus.COMPLETED==messagePaymentOrderStatus){
                        log.info("processing completed payment response with if: {}",message.getId());
                        paymentResponseMessageListener.paymentCompleted(
                                orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(message)
                        );
                    } else if (PaymentStatus.CANCELLED==messagePaymentOrderStatus||PaymentStatus.FAILED==messagePaymentOrderStatus) {
                        log.info("processing failed payment response with if: {}",message.getId());
                        paymentResponseMessageListener.paymentCancelled(
                                orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(message)
                        );

                }


                }

        );

    }
}
