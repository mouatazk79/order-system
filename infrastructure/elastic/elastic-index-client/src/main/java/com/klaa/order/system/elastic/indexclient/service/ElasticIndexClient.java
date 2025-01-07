package com.klaa.order.system.elastic.indexclient.service;

import com.klaa.order.system.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient <T extends IndexModel>{
    List<String> save(List<T> documents);

}
