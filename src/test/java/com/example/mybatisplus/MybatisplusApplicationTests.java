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
    public void testPage01 () {
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

    @Test
    public void testPage02 () {
        // SELECT COUNT(*) AS total FROM t_user WHERE age > ?
        // select id,user_name,age,email from t_user where age > ? LIMIT ?

        Page<User> page = new Page<>(1, 3);
        userMapper.selectPageVo(page, 20);
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
        System.out.println("当前页:"+page.getCurrent());
        System.out.println("每页显示的条数:"+page.getSize());
        System.out.println("总记录数:"+page.getTotal());
        System.out.println("总页数:"+page.getPages());
        System.out.println("是否有上一页:"+page.hasPrevious());
        System.out.println("是否有下一页:"+page.hasNext());
    }

}
