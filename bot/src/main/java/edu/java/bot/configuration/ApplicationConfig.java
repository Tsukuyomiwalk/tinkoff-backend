package edu.java.bot.configuration;

import edu.java.bot.utils.Types;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;
import java.time.Duration;
import java.util.Set;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    Uris urls,
    RateLimiter rateLimiter,
    Retry retrySpecification
) {
    public record Uris(
        @NotNull
        @DefaultValue("http://localhost:8080")
        String scrapper
    ) {
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
}
