package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public WebClient githubWebClient(ApplicationConfig applicationConfig) {
        return WebClient.builder()
            .baseUrl(applicationConfig.gitHubUrl())
            .build();
    }

    @Bean
    public WebClient stackOverflowWebClient(ApplicationConfig applicationConfig) {
        return WebClient.builder()
            .baseUrl(applicationConfig.stackOverflowUrl())
            .build();
    }

    @Bean
    public WebClient botClient(ApplicationConfig cfg) {
        return WebClient.builder()
            .baseUrl(cfg.stackOverflowUrl())
            .build();
    }
}

