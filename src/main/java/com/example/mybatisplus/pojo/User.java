package com.example.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data // 生成getter、setter、toString、equals、hashCode方法
// @TableName("t_user") // 设置实体类对应的表名
public class User {

    // @TableId(value = "uid") // value属性指定主键字段名，如果实体类字段名与数据库字段名一致，可以省略value属性
    // IdType用于主键生成策略 AUTO自动递增（数据库也必须设置为自动递增） ASSIGN_ID表示分配ID（主键类型为Number(Long和Integer)或String）
    // ID_WORKER表示雪花算法（主键类型为Number(Long和Integer)或String） UUID表示随机UUID（主键类型为String）
    // @TableId(type = IdType.AUTO)
    @TableId(type = IdType.ASSIGN_ID)
    private long id;
    private String userName; // MybatisPlus会自动将驼峰命名的属性转换为下划线命名的字段
    @TableField("age") // 用于指定实体类属性对应的数据库字段名，如果实体类属性名与数据库字段名一致，可以省略value属性
    private Integer userAge;
    private String email;
    @TableLogic // 用于逻辑删除，会自动在SQL语句中加上is_deleted = 0
    private Integer isDeleted;
}
