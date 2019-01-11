package com.zhaowenx.manage.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis处理类
 * @author zwk
 */
@Component
public class RedisUtil {
    private Logger logger = Logger.getLogger(RedisUtil.class);

    private final long DEFAULT_EXPIRE_TIME = 3600;

    private final String PREFIX = "transmanage:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(StringUtils.join(PREFIX,key), JSON.toJSONString(value), DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("redis set cache value error :  ： ", e);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void set(String key, String value, Long time) {
        try {
            redisTemplate.opsForValue().set(StringUtils.join(PREFIX,key), value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("redis set cache value error :  ： ", e);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(StringUtils.join(PREFIX,key), value);
        } catch (Exception e) {
            logger.error("redis set cache value error :  ： ", e);
        }
    }

    /**
     * 获取key的value，返回一个calzz类型的对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        try {
            String value = redisTemplate.opsForValue().get(StringUtils.join(PREFIX,key));
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            logger.error("redis get cache value error : ", e);
            return null;
        }
    }

    /**
     * 获取key的value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(StringUtils.join(PREFIX,key));
        } catch (Exception e) {
            logger.error("redis get cache value error : ", e);
            return null;
        }
    }

    /**
     * 设置超时时间
     *
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        try {
            redisTemplate.expire(StringUtils.join(PREFIX,key), seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("redis set cache time error : ", e);
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void remove(String key) {
        try {
            redisTemplate.delete(StringUtils.join(PREFIX,key));
        } catch (Exception e) {
            logger.error("redis remove cache value error :  ： ", e);
        }
    }
}