package edu.java.utils;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class LinearRetry extends Retry {

    private final Duration backoffInterval;
    private final int maxAttempts;

    public LinearRetry(int maxAttempts, Duration backoffInterval) {
        this.maxAttempts = maxAttempts;
        this.backoffInterval = backoffInterval;
    }

    public LinearRetry filter(Predicate<Throwable> filter) {
        return this;
    }

    public LinearRetry onRetryExhaustedThrow(
        BiFunction<LinearRetry, RetrySignal, Throwable> retryExhaustedGenerator
    ) {
        return this;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> flux) {
        return flux.flatMap(this::generateRetry);
    }

    private Publisher<?> generateRetry(RetrySignal retrySignal) {
        if (retrySignal.totalRetries() < maxAttempts) {
            return Flux.just(retrySignal)
                .delayElements(backoffInterval);
        } else {
            return Flux.error(new RuntimeException("Retry exhausted after " + retrySignal.totalRetries()));
        }
    }
}
