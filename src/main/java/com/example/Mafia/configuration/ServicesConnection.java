package com.example.Mafia.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "users")
public class ServicesConnection {

    private String urlTasks;
    private String urlBands;

    public String getUrlTasks() {
        return urlTasks;
    }

    public void setUrlTasks(String urlTasks) {
        this.urlTasks = urlTasks;
    }

    public String getUrlBands() {
        return urlBands;
    }

    public void setUrlBands(String urlBands) {
        this.urlBands = urlBands;
    }

}
