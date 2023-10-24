package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatisplus.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    Map<String, Object> selectMapById(Long id);
}
