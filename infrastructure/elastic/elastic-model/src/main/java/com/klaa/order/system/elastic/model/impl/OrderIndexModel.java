package com.klaa.order.system.elastic.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klaa.order.system.elastic.model.IndexModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;
@NoArgsConstructor
@Data
@Document(indexName = "order")
public class OrderIndexModel implements IndexModel {
    @Id
    @JsonProperty
    @Field(type = FieldType.Text, name = "id")
    private String id;
    @JsonProperty
    @Field(type = FieldType.Nested, name = "position")
    private IndexPosition position;
    @JsonProperty
    @Field(type = FieldType.Nested, name = "destination")
    private IndexPosition destination;
    @JsonProperty
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100, name = "price")
    private BigDecimal price;
    @JsonProperty
    @Field(type = FieldType.Keyword, name = "driverId")
    private UUID driverId;
    @JsonProperty
    @Field(type = FieldType.Keyword, name = "trackingId")
    private UUID trackingId;
    @JsonProperty
    @Field(type = FieldType.Keyword, name = "orderStatus")
    private String orderStatus;
}
