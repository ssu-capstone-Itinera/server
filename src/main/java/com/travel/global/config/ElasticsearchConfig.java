 package com.travel.global.config;


 import co.elastic.clients.elasticsearch.ElasticsearchClient;
 import co.elastic.clients.json.jackson.JacksonJsonpMapper;
 import co.elastic.clients.transport.ElasticsearchTransport;
 import co.elastic.clients.transport.rest_client.RestClientTransport;
 import org.apache.http.HttpHost;
 import org.elasticsearch.client.RestClient;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

 @Configuration
 @EnableElasticsearchRepositories(basePackages = "com.travel.domain")
 public class ElasticsearchConfig {

     @Value("${spring.elasticsearch.uris}")
     private String elasticsearchUri;

     @Bean
     public ElasticsearchClient elasticsearchClient() {

         RestClient restClient = RestClient.builder(
                 HttpHost.create(elasticsearchUri)
         ).build();


         ElasticsearchTransport transport = new RestClientTransport(
                 restClient, new JacksonJsonpMapper());


         return new ElasticsearchClient(transport);
     }
 }
