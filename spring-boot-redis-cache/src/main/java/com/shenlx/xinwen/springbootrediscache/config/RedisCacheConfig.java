package com.shenlx.xinwen.springbootrediscache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-13 21:54
 **/
@Configuration
@Slf4j
public class RedisCacheConfig {
    @Autowired
    private Environment env;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
        redisConf.setHostName(env.getProperty("spring.redis.host"));
        redisConf.setPort(Integer.parseInt(env.getProperty("spring.redis.port")));
        redisConf.setPassword(RedisPassword.of(env.getProperty("spring.redis.password")));
        return new LettuceConnectionFactory(redisConf);
    }

    @Bean(name = "redisCacheConfiguration")
    public RedisCacheConfiguration cacheConfiguration() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .disableCachingNullValues();
        return cacheConfig;
    }

//    @Bean(name ="redisCacheManager" )
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory())
//                .cacheDefaults(cacheConfiguration())
//                .transactionAware()
//                .build();
//        return rcm;
//    }

    /**
     * 自定义缓存key的生成类实现
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

//    // 缓存管理器
//    @Bean
//    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//        //RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        // 设置缓存过期时间
//        //cacheManager
//       // cacheManager.setDefaultExpiration(10000);
//        return cacheManager;
//    }

    @Bean(name ="redisCacheManager" )
    public RedisCacheManager redisCacheManager(LettuceConnectionFactory lettuceConnectionFactory){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))//设置缓存过期时间
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));   //设置序列化器
        RedisCacheManager build = RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(config)
                .build();
        return build;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        setSerializer(template);// 设置序列化工具
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    //创建其它缓存管理类，使用的时候，根据cacheManager来指定使用哪个cache管理器，默认使用被 @Primary修饰的。例如：  @Cacheable(cacheNames = "xxxx",key = "#root.args[0]",cacheManager = "cacheManagerOneMinute")
    //这样就可以根据业务需要配置不同的cache管理器，一般用于设置redis不同的失效时间，此为设置失效时间为1分钟
//    @Bean
//    public CacheManager cacheManagerOneMinute(RedisConnectionFactory redisConnectionFactory,CacheProperties cahceProperties){
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(redisCacheConfiguration(cahceProperties,60000L))
//                .build();
//    }
//
//    //这样就可以根据业务需要配置不同的cache管理器，一般用于设置redis不同的失效时间，此为设置失效时间为1小时
//    @Bean
//    public CacheManager cacheManagerOneHour(RedisConnectionFactory redisConnectionFactory, CacheProperties cahceProperties){
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(redisCacheConfiguration(cahceProperties,3600000L))
//                .build();
//    }
}
