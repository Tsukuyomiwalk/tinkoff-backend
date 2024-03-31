package edu.java.bot.limiter;

import io.github.bucket4j.Bucket;

public interface RateLimiter {
    Bucket resolve(String ip);
}
