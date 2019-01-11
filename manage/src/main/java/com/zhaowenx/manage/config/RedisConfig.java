package com.zhaowenx.manage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zhaowenx on 2018/8/26.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "jedisPool")
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool开始注入");
        logger.info("redis地址：" + host + ":" + port);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = null;

        if (password == null || "".equals(password)){
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        }
//        System.out.println(jedisPool.getResource().ping());

        logger.info("---------JedisPool注入成功-----------");
        return jedisPool;
    }
}
