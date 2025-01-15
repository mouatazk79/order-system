package com.klaa.order.system.elastic.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.AllArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.klaa.order.system.elastic")
@AllArgsConstructor
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

//    @Bean
//    public ElasticsearchClient elasticsearchClient() throws IOException {
//        UriComponents serverUri = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();
//        RestClient restClient = RestClient.builder(new HttpHost(
//                Objects.requireNonNull(serverUri.getHost()),
//                serverUri.getPort(),
//                serverUri.getScheme()
//        )).setRequestConfigCallback(
//                requestConfigBuilder ->
//                        requestConfigBuilder
//                                .setConnectTimeout(elasticConfigData.getConnectTimeoutMs())
//                                .setSocketTimeout(elasticConfigData.getSocketTimeoutMs())
//        ).build();
//
//        RestClientTransport transport = new RestClientTransport(
//                restClient,
//                new JacksonJsonpMapper()
//        );
//
//        return new ElasticsearchClient(transport);
//    }


    @Override
    public ClientConfiguration clientConfiguration() {
          return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                  .withConnectTimeout(elasticConfigData.getConnectTimeoutMs())
                  .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                  .build();
    }
    }

