package com.es.gatewayService.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

//@Configuration
//@Slf4j
public class RedisConfig {
//    private final Environment env;
//
//    public RedisConfig(Environment env) {
//        this.env = env;
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(Objects.requireNonNull(env.getProperty("spring.redis.host")));
//        redisStandaloneConfiguration.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.redis.port"))));
//        redisStandaloneConfiguration.setPassword(env.getProperty("spring.redis.password"));
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setEnableTransactionSupport(true);
//        return template;
//    }
}