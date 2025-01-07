package com.klaa.order.system.elastic.queryclient.service;

import com.klaa.order.system.elastic.model.IndexModel;

import java.util.List;
import java.util.Optional;

public interface ElasticQueryClient<T extends IndexModel> {

    Optional<T> getIndexModelById(String id);

    List<T> getIndexModelByOrderStatus(String text);

    List<T> getAllIndexModels();
}