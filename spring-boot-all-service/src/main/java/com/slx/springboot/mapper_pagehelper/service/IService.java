package com.slx.springboot.mapper_pagehelper.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IService<T> {
    Long getSequence(@Param("seqName") String seqName);

    List<T> selectAll();

    T selectByKey(Object key);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);
}
