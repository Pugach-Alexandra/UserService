package com.example.Mafia;

import com.example.Mafia.configuration.ConnectDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;

@SpringBootApplication
@EnableConfigurationProperties({ConnectDatabase.class})
public class
MafiaApplication {

	public static void main(String[] args) {

		final Logger LOGGER = LoggerFactory.getLogger(MafiaApplication.class);
		SpringApplication.run(MafiaApplication.class, args);

	}
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(ConnectDatabase astraProperties) {

		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);

	}
	@Bean
	public RestTemplate getRestTemplate() {

		return new RestTemplate();

	}
}
