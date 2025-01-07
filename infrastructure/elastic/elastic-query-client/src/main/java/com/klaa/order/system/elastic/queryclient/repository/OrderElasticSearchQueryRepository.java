package com.klaa.order.system.elastic.queryclient.repository;

import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderElasticSearchQueryRepository extends ElasticsearchRepository<OrderIndexModel,String> {
        List<OrderIndexModel> findByOrderStatus(String status);
}
