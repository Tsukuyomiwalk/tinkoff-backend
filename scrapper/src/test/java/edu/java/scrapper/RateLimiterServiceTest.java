package edu.java.scrapper;

import edu.java.limiter.RateLimiter;
import edu.java.limiter.RateLimiterService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucketBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RateLimiterServiceTest {

    @Mock
    private RateLimiter rateLimiter;

    @InjectMocks
    private RateLimiterService rateLimiterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResolve2() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(1)));
        Bucket bucket = new LocalBucketBuilder().addLimit(limit).build();
        when(rateLimiter.resolve("127.0.0.1")).thenReturn(bucket);

        for (int i = 0; i < 5; i++) {
            ConsumptionProbe probe = rateLimiterService.resolve("127.0.0.1").tryConsumeAndReturnRemaining(1);
            assertTrue(probe.isConsumed());
        }
    }

    @Test
    void testResolve1() {
        Bandwidth limit = Bandwidth.classic(2, Refill.intervally(2, Duration.ofSeconds(1)));
        Bucket bucket = new LocalBucketBuilder().addLimit(limit).build();
        when(rateLimiter.resolve("127.0.0.1")).thenReturn(bucket);
        ConsumptionProbe probe = rateLimiterService.resolve("127.0.0.1").tryConsumeAndReturnRemaining(5);
        assertTrue(probe.isConsumed());
    }
}
