package com.itsystem.springbootorderfood.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.pojo
 * @className: Custom
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/10/30 15:00
 * @version: 1.0
 */
@Data
@TableName("customer")
public class Customer {
    private Integer id;
    private String customerName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String cimage;
}
