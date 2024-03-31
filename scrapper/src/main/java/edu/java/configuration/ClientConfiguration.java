package edu.java.configuration;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

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
            .baseUrl(cfg.bot())
            .build();
    }

    @Bean
    @SuppressWarnings("MagicNumber")
    public Retry retry() {
        return Retry.backoff(3, Duration.ofMillis(100))
            .maxBackoff(Duration.ofSeconds(10))
            .jitter(0.5)
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                new RuntimeException("Retry exhausted after " + retrySignal));
    }
}

