package com.example.Mafia.configuration;

import java.io.File;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datastax.astra")
@Getter
@Setter
public class ConnectDatabase {

    private File secureConnectBundle;

}
