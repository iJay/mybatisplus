package com.example.mybatisplus;

import com.example.mybatisplus.pojo.User;
import com.example.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Array;
import java.util.ArrayList;

@SpringBootTest
public class MybatisPlusServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetCount () {
        // SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println("count: " + count);
    }

    @Test
    public void testBatchInsert () {
        // INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        // mapper接口中没有批量插入的方法，但是service中有
        // service中的批量插入方法是调用mapper中的批量插入方法实现的
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId((long) (10 + i));
            user.setUserName("张三" + i);
            user.setUserAge(18 + i);
            users.add(user);
        }
        boolean b = userService.saveBatch(users);
        System.out.println("b: " + b);
    }
}
