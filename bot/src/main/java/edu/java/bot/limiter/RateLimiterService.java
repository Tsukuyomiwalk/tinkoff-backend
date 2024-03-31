package edu.java.bot.limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BlockingBucket;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.BucketState;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimiterService implements RateLimiter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final int maxRequestsPerSecond = 10;
    @Override
    public Bucket resolve(String ip) {
        return buckets.computeIfAbsent(ip, k -> {
            Bandwidth limit =
                Bandwidth.classic(maxRequestsPerSecond, Refill.intervally(maxRequestsPerSecond, Duration.ofSeconds(1)));
            return Bucket.builder()
                .addLimit(limit)
                .build();
        });
    }
}
