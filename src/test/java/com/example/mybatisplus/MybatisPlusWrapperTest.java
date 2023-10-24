package com.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
}
