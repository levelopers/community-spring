package com.forum.forum.exception.handler;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * @author ：Zack
 * @date ：Created in 2020/7/16 12:05
 */
public class CustomCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {

    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {

    }

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {

    }
}
