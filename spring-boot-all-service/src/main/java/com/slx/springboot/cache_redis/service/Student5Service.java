package com.slx.springboot.cache_redis.service;

import com.slx.springboot.common.entity.Student;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "student5")
public interface Student5Service {
    @CachePut(key = "#p0.sno")
    public abstract Student update(Student student);

    @CacheEvict(key = "#p0", allEntries = true)
    public abstract void deleteStudentBySno(String sno);

    @Cacheable(key = "#p0")
    public abstract Student queryStudentBySno(String sno);
}
