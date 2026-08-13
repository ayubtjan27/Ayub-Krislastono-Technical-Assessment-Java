package com.ayub.assessment.api.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    ElasticsearchClient elasticsearchClient(
            @Value("${elasticsearch.url:http://localhost:9200}") String url) {

        RestClient rest = RestClient
                .builder(HttpHost.create(url))
                .build();

        return new ElasticsearchClient(
                new RestClientTransport(
                        rest,
                        new JacksonJsonpMapper()
                )
        );
    }
}