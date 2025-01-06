package com.klaa.order.system.elastic.model.impl;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class IndexPosition {
    @Field(type = FieldType.Text, name = "streetAddress")
    private String streetAddress;
    @Field(type = FieldType.Text, name = "zipCode")
    private String zipCode;
    @Field(type = FieldType.Text, name = "city")
    private String city;
}
