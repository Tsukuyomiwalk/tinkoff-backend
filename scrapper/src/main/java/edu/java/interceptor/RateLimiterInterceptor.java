package edu.java.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    public final Map<String, Instant> lastRequestTimeMap = new HashMap<>();

    @Override
    @SuppressWarnings("MagicNumber")
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler)
        throws RuntimeException {
        String ipAddress = request.getRemoteAddr();
        Instant now = Instant.now();
        if (lastRequestTimeMap.containsKey(ipAddress)) {
            Instant lastRequestTime = lastRequestTimeMap.get(ipAddress);
            Duration timeElapsed = Duration.between(lastRequestTime, now);

            int maxRequestsPerSecond = 10;
            int requestsPerSecond = (int) (maxRequestsPerSecond * ((double) timeElapsed.toMillis() / 1000));
            if (requestsPerSecond >= maxRequestsPerSecond) {
                throw new TooManyRequestsException();
            }
        }
        lastRequestTimeMap.put(ipAddress, now);
        return true;
    }
}

