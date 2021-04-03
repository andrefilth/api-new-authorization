package com.picpay.api.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/***
 * Classe de configuração para Spring Embedded Redis
 */
@TestConfiguration
public class TestRedisConfiguration {

    private final RedisServer redisServer;

    public TestRedisConfiguration(@Value("${spring.redis.port}") final int redisPort) {
        this.redisServer = new RedisServer(redisPort);
    }

    @PostConstruct
    public void start() {
        redisServer.start();
    }

    @PreDestroy
    public void stop() {
        redisServer.stop();
    }
}
