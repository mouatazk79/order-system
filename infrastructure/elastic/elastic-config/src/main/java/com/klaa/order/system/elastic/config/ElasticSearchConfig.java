package com.klaa.order.system.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.AllArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.Assert;


@Configuration
@EnableElasticsearchRepositories(
        basePackages = "com.klaa.order.system.elastic.indexclient"
)
@AllArgsConstructor
public class ElasticSearchConfig extends ReactiveElasticsearchConfiguration {
    private final ElasticConfigData elasticConfigData;


    @Override
    @Bean
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticConfigData.getConnectionUrl())
                .withConnectTimeout(elasticConfigData.getConnectTimeoutMs())
                .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                .build();

    }

    @Bean
    public RestClient elasticsearchRestClient(ClientConfiguration clientConfiguration) {
        return ElasticsearchClients.getRestClient(clientConfiguration);
    }
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = elasticsearchRestClient(clientConfiguration());
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );
        return new ElasticsearchClient(transport);
    }
    @Bean
    public ReactiveElasticsearchOperations reactiveElasticsearchOperations(ElasticsearchConverter elasticsearchConverter, ReactiveElasticsearchClient reactiveElasticsearchClient) {
        ReactiveElasticsearchTemplate template = new ReactiveElasticsearchTemplate(reactiveElasticsearchClient, elasticsearchConverter);
        template.setRefreshPolicy(this.refreshPolicy());
        return template;
    }





}
