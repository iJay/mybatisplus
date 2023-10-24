package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Map<String, Object> selectMapById(Long id);

    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
}
