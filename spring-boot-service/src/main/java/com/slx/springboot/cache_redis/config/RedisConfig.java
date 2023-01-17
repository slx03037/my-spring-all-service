package com.slx.springboot.cache_redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 21:17
 **/
//@Configuration
//public class RedisConfig extends CachingConfigurerSupport {
//    // 自定义缓存key生成策略
//    @Override
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
//                StringBuffer sb = new StringBuffer();
//                sb.append(target.getClass().getName());
//                sb.append(method.getName());
//                for (Object obj : params) {
//                    sb.append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    // springboot2.0不支持缓存管理器
////    @Bean
////    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
////        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate.);
////        // 设置缓存过期时间
////        cacheManager.setDefaultExpiration(10000);
////        return cacheManager;
////    }
//
//    @SuppressWarnings("rawtypes")
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(null);
//        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                .cacheDefaults(redisCacheConfiguration).build();
//    }
//
////    @Bean
////    @SuppressWarnings({"rawtypes", "unchecked"})
////    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory){
////        RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
////        redisTemplate.setConnectionFactory(connectionFactory);
////        //使用Jackson2JsonRedisSerializer替换默认的序列化规则
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper objectMapper=new ObjectMapper();
////        objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
////        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
////
////
////        //设置value的序列化规则
////        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
////        //设置key的序列化规则
////        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.afterPropertiesSet();
////        return redisTemplate;
////    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate template = new StringRedisTemplate(factory);
//        setSerializer(template);// 设置序列化工具
//        template.afterPropertiesSet();
//        return template;
//    }
//
//    private void setSerializer(StringRedisTemplate template) {
//        @SuppressWarnings({ "rawtypes", "unchecked" })
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//    }
//}
