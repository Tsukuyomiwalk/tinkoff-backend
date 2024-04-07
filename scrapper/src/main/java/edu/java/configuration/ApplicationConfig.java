package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.sql.Types;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Bean
    @NotNull
    Scheduler scheduler,
    AccessType databaseAccessType,
    RateLimiter rateLimiter,
    Retry retry,
    TopicInfo topic,
    Boolean useQueue,

    @NotNull
    @DefaultValue("https://api.github.com")
    String gitHubUrl,

    @NotNull
    @DefaultValue("https://api.stackexchange.com/2.3")
    String stackOverflowUrl,
    @NotNull
    @DefaultValue("http://localhost:8090")
    String bot
) {

    public enum AccessType { JDBC, JPA }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record RateLimiter(
        boolean enable,
        @NotNull Integer limit,
        @NotNull Integer refillPerMinute
    ) {
    }

    public record Retry(
        @NotNull Types type,
        @NotNull Integer maxAttempts,
        Duration delay,
        Set<Integer> codes
    ) {
    }
    public record TopicInfo(
        @NotNull String name,
        @NotNull Integer partitions,
        @NotNull Integer replicas
    ) {
}
