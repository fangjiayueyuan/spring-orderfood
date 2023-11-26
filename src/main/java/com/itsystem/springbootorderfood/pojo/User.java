package com.itsystem.springbootorderfood.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.pojo
 * @className: User
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/10/30 11:13
 * @version: 1.0
 */
@Data
@TableName("user")
public class User {
    private Integer id;
    private String password;
    private String username;
    private String email;
    private String phone;
    private String image;
}
