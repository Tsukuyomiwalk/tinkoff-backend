package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
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
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }
}
