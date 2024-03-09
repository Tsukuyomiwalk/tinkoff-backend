package edu.java.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration

public class ClientConfiguration {
    @Bean
    public WebClient scrapperWebClient(ApplicationConfig applicationConfig) {
        return WebClient.builder()
            .baseUrl(applicationConfig.urls().scrapper())
            .build();
    }
}

