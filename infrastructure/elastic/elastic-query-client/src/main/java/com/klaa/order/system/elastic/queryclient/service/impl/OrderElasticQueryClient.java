package com.klaa.order.system.elastic.queryclient.service.impl;

import com.klaa.order.system.elastic.model.impl.OrderIndexModel;
import com.klaa.order.system.elastic.queryclient.repository.OrderElasticSearchQueryRepository;
import com.klaa.order.system.elastic.queryclient.service.ElasticQueryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderElasticQueryClient implements ElasticQueryClient<OrderIndexModel> {
    private final OrderElasticSearchQueryRepository orderElasticSearchQueryRepository;
    @Override
    public Optional<OrderIndexModel> getIndexModelById(String id) {
        return orderElasticSearchQueryRepository.findById(id);
    }

    @Override
    public List<OrderIndexModel> getIndexModelByOrderStatus(String status) {
        return orderElasticSearchQueryRepository.findByOrderStatus(status);
    }

    @Override
    public List<OrderIndexModel> getAllIndexModels() {
        return getListFromIterable(orderElasticSearchQueryRepository.findAll());
    }
    private  <T> List<T> getListFromIterable(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
