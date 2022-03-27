package com.example.Mafia.configuration;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.time.Duration;

@Configuration
public class ConfigurationClass {

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(ConnectDatabase astraProperties) {

        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);

    }

    /*@Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();

    }*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(60000))
                .setReadTimeout(Duration.ofMillis(60000))
                .build();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory factory(){
        return new HttpComponentsClientHttpRequestFactory();
    }

}
