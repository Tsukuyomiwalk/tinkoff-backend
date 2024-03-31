package edu.java.limiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
