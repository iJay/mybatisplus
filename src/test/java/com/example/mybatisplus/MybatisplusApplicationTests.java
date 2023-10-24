package com.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.mapper.UserMapper;
import com.example.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisplusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPage () {
        // SELECT id,user_name,age AS userAge,email,is_deleted FROM t_user WHERE is_deleted=0 LIMIT ?
        Page<User> userPage = new Page<>(1, 3);
        userMapper.selectPage(userPage, null);
        List<User> users = userPage.getRecords();
        users.forEach(System.out::println);
        System.out.println("当前页：" + userPage.getCurrent());
        System.out.println("每页显示条数：" + userPage.getSize());
        System.out.println("总条数：" + userPage.getTotal());
        System.out.println("总页数：" + userPage.getPages());
        System.out.println("是否有上一页：" + userPage.hasPrevious());
        System.out.println("是否有下一页：" + userPage.hasNext());
    }

}
