package edu.java.scrapper;

import edu.java.interceptor.RateLimiterInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimiterInterceptorTest {

    private RateLimiterInterceptor interceptor;

    @BeforeEach
    void setUp() {
        interceptor = new RateLimiterInterceptor();
    }

    @Test
    @DisplayName("Test RateLimiterInterceptor when requests are within limit")
    void testRateLimiterInterceptorWithinLimit()  {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        String ipAddress = "127.0.0.1";
        when(request.getRemoteAddr()).thenReturn(ipAddress);
        assertTrue(interceptor.preHandle(request, response, handler));

        assertTrue(interceptor.preHandle(request, response, handler));
    }

    @Test
    @DisplayName("Test RateLimiterInterceptor when requests exceed limit")
    void testRateLimiterInterceptorExceedLimit() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        String ipAddress = "127.0.0.1";

        when(request.getRemoteAddr()).thenReturn(ipAddress);

        Instant now = Instant.now();
        Map<String, Instant> lastRequestTimeMap = new HashMap<>();
        lastRequestTimeMap.put(ipAddress, now.minusSeconds(10));
        interceptor.lastRequestTimeMap.putAll(lastRequestTimeMap);

        assertThrows(RuntimeException.class, () -> interceptor.preHandle(request, response, handler));
    }
}
