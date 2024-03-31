package edu.java.configuration;

import edu.java.utils.LinearRetry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

@Configuration
@EnableConfigurationProperties(ApplicationConfig.class)
@RequiredArgsConstructor
public class RetryConfig {
    private final ApplicationConfig applicationConfig;

    @Bean
    @SuppressWarnings("MagicNumber")
    @ConditionalOnProperty(prefix = "app", name = "retry.type", havingValue = "fixed")
    public Retry fixedRetry() {
        var retrySpec = applicationConfig.retry();
        return Retry.fixedDelay(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .jitter(0.5)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry.type", havingValue = "linear")
    public Retry linearRetry() {
        var retrySpec = applicationConfig.retry();
        return new LinearRetry(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    @Bean
    @SuppressWarnings("MagicNumber")
    @ConditionalOnProperty(prefix = "app", name = "retry.type", havingValue = "exponential")
    public Retry exponentialRetry() {
        var retrySpec = applicationConfig.retry();
        Retry.max(0);
        return Retry.backoff(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .jitter(0.5)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

}
