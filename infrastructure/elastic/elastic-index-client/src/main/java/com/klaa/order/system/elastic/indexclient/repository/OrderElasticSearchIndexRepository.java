package com.klaa.order.system.elastic.indexclient.repository;

import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderElasticSearchIndexRepository extends ElasticsearchRepository<OrderIndexModel,String> {
}
