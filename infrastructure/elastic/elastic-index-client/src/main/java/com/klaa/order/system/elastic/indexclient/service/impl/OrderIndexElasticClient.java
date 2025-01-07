package com.klaa.order.system.elastic.indexclient.service.impl;

import com.klaa.order.system.elastic.indexclient.repository.OrderElasticSearchIndexRepository;
import com.klaa.order.system.elastic.indexclient.service.ElasticIndexClient;
import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderIndexElasticClient implements ElasticIndexClient<OrderIndexModel> {
    private final OrderElasticSearchIndexRepository orderElasticSearchIndexRepository;
    @Override
    public List<String> save(List<OrderIndexModel> documents) {
        List<OrderIndexModel> repositoryResponse = (List<OrderIndexModel>) orderElasticSearchIndexRepository.saveAll(documents);
        return repositoryResponse.stream().map(OrderIndexModel::getId).toList();
    }
}
