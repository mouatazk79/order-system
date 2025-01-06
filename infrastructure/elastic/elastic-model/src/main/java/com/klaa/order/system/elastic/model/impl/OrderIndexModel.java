package com.klaa.order.system.elastic.model.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klaa.order.system.elastic.model.IndexModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Document(indexName = "order.index")
public class OrderIndexModel implements IndexModel {
    @JsonProperty
    @Field(type = FieldType.Text, name = "streetAddress")
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
    @Field(type = FieldType.Keyword, name = "uuid")
    private UUID driverId;
    @JsonProperty
    @Field(type = FieldType.Keyword, name = "uuid")
    private UUID trackingId;
    @JsonProperty
    @Field(type = FieldType.Keyword, name = "uuid")
    private String orderStatus;
}
