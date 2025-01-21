package com.klaa.order.system.kafkatoelastic.messaging.mapper;

import com.klaa.order.system.elastic.model.impl.IndexPosition;
import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import com.klaa.order.system.kafka.model.elastic.OrderElasticMessageAvroModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
public class ElasticOrderDataMapper {
    public List<OrderIndexModel> orderElasticMessageAvroModelToOrderIndexModel(OrderElasticMessageAvroModel message) {
        if (message == null) {
            return Collections.emptyList();
        }

        OrderIndexModel orderIndexModel = new OrderIndexModel();
        orderIndexModel.setId(message.getId().toString());
        orderIndexModel.setPosition(
                new IndexPosition(
                        message.getPosition().getStreetAddress(),
                        message.getPosition().getZipCode(),
                        message.getPosition().getCity()
                )
        );
        orderIndexModel.setDestination(
                new IndexPosition(
                        message.getDestination().getStreetAddress(),
                        message.getDestination().getZipCode(),
                        message.getDestination().getCity()
                )
        );
        orderIndexModel.setPrice(message.getPrice());
        orderIndexModel.setDriverId(UUID.fromString(message.getDriverId().toString()));
        orderIndexModel.setTrackingId(UUID.fromString(message.getTrackingId().toString()));
        orderIndexModel.setOrderStatus(message.getOrderStatus().toString());

        return Collections.singletonList(orderIndexModel);
    }
}
