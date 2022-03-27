package com.example.Mafia;

import com.example.Mafia.configuration.ConnectDatabase;
import com.example.Mafia.configuration.ServicesConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({ConnectDatabase.class, ServicesConnection.class})
public class
MafiaApplication {

	public static void main(String[] args) {

		SpringApplication.run(MafiaApplication.class, args);

	}

}
