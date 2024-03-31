package edu.java.bot.configuration;

import edu.java.bot.utils.LinearRetry;
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
    @ConditionalOnProperty(prefix = "app", name = "retry.back-off-type", havingValue = "fixed")
    public Retry fixedRetry() {
        var retrySpec = applicationConfig.retrySpecification();
        return Retry.fixedDelay(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .jitter(0.5)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry.back-off-type", havingValue = "linear")
    public Retry linearRetry() {
        var retrySpec = applicationConfig.retrySpecification();
        return new LinearRetry(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "retry.back-off-type", havingValue = "exponential")
    public Retry exponentialRetry() {
        var retrySpec = applicationConfig.retrySpecification();
        Retry.max(0);
        return Retry.backoff(retrySpec.maxAttempts(), retrySpec.delay())
            .filter(throwable -> throwable instanceof WebClientResponseException)
            .jitter(0.5)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure());
    }

}
