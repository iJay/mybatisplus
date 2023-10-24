package com.example.mybatisplus;

import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest // 标记为SpringBoot测试类
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper; // 这里会报错，但不影响运行 解决方法： 在mapper接口上加@Mapper或者@Repository注解

    @Test // 标记为测试方法
    public void testSelectList() {
        // SELECT id,name,age,email FROM user
        List<User> userList = userMapper.selectList(null); // 查询所有用户
        userList.forEach(System.out::println); // 打印输出
    }

    @Test
    public void testInsert() {
        // INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        user.setId(120L);
        user.setUserName("李四2");
        user.setUserAge(18);
        user.setEmail("123123@163.com");
        int insert = userMapper.insert(user);
        System.out.println("insert: " + insert);
        System.out.println("id: " + user.getId());
    }

    @Test
    public void testDelete () {
        // DELETE FROM user WHERE id=?
        int delete = userMapper.deleteById(0L);
        System.out.println("delete: " + delete);
    }

    @Test
    public void testDeleteByMap () {
        // DELETE FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 18);
        int delete = userMapper.deleteByMap(map);
        System.out.println("delete: " + delete);
    }

    @Test
    public void testDeleteBatchByIds () {
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
        int ids = userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 3L));
        System.out.println("ids: " + ids);
    }

    @Test
    public void testUpdate () {
        // UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setId(4L);
        user.setUserName("李四");
        user.setEmail("lisi@163.com");
        int result = userMapper.updateById(user);
        System.out.println("result: " + result);
    }

    @Test
    public void testSelectById () {
        // SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelelctByIds () {
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap () {
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", 20);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectMapById () {
        // SELECT id,name,age,email FROM user WHERE id=?
        Map<String, Object> map = userMapper.selectMapById(1L);
        System.out.println(map);
    }
}
