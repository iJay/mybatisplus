package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testWrapper01 () {
        // 查询用的条件构造器对象
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        // 查询用户名包含a 年龄20~30 邮箱信息部为null的用户
        userQueryWrapper.like("user_name", "a") // 这里的name是数据库中的字段名，不是实体类中的属性名
                .between("age", 20, 30)
                .isNotNull("email");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testWrapper02 () {
        // SELECT id,user_name,age AS userAge,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,id ASC
        // 查询用户信息 按年龄降序 年龄相同 按id升序排
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testWrapper03 () {
        // UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 因为添加了逻辑删除，所以这里的删除操作变成了更新操作
        // 删除邮箱为null的用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.isNull("email");
        int delete = userMapper.delete(userQueryWrapper);
        System.out.println("delete: " + delete);
    }

    @Test
    public void testWrapper04 () {
        // UPDATE t_user SET age=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
        // 将年龄大于20并且用户名含有a 或者 邮箱为null的用户信息修改
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.gt("age", 20).like("user_name", "a").or().isNull("email");
        User user = new User();
        user.setUserAge(72);
        int update = userMapper.update(user, userQueryWrapper);
        System.out.println("update: " + update);
    }

    /**
     * 条件优先级测试
     */
    @Test
    public void testWrapper05 () {
        // UPDATE t_user SET age=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        // 用户名含有a 并且 (年龄大于20或者邮箱为null)的用户信息修改
        // lambda中的条件优先执行
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("user_name", "a").and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setUserAge(120);
        int update = userMapper.update(user, userQueryWrapper);
        System.out.println("update: " + update);
    }

    @Test
    public void testWrapper06 () {
        // SELECT user_name,age,email FROM t_user WHERE is_deleted=0
        // 查询用户名 年龄 邮箱信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("user_name", "age", "email");
        userMapper.selectList(userQueryWrapper).forEach(System.out::println);
    }

    /**
     * 子查询语句
     */
    @Test
    public void testWrapper07 () {
        // SELECT id,user_name,age AS userAge,email,is_deleted FROM t_user WHERE is_deleted=0 AND (id IN (Select id from t_user where id <= 100))
        // 查询id小于等于100的用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .inSql("id", "select id from t_user where id <= 100");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testWrapper08 () {
        // UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        // 用户名含有a 并且 (年龄大于100或者邮箱为null)的用户信息修改
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.like("user_name", "a").and(i -> i.gt("age", 100).or().isNull("email")).set("user_name", "小黑").set("email", "hblx2022@163.com");
        int update = userMapper.update(null, userUpdateWrapper);
        System.out.println("update: " + update);
    }

    /**
     * 模拟开发中的组装条件
     * 这种判断太过繁琐
     */
    @Test
    public void testWrapper09 () {
        // SELECT id,user_name,age AS userAge,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
        String name = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) { // 判断name不为空字符串 不为空白字符串 不为null
            userQueryWrapper.like("user_name", name);
        }
        if(ageBegin != null) {
            userQueryWrapper.ge("age", ageBegin);
        }
        if (ageEnd != null) {
            userQueryWrapper.le("age", ageEnd);
        }
        userMapper.selectList(userQueryWrapper).forEach(System.out::println);
    }

    /**
     * 使用condition解决上述判断繁琐的问题
     */
    @Test
    public void testWrapper10 () {
        // SELECT id,user_name,age AS userAge,email,is_deleted FROM t_user WHERE is_deleted=0 AND (User_name LIKE ? AND age <= ?)
        String name = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(name), "User_name", name)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        userMapper.selectList(userQueryWrapper).forEach(System.out::println);
    }
}
