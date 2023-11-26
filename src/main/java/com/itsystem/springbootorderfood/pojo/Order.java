package com.itsystem.springbootorderfood.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.pojo
 * @className: Order
 * @author: fangjiayueyuan
 * @description: 订单类
 * @date: 2023/11/4 16:46
 * @version: 1.0
 */
@Data
@TableName("f_order")
public class Order {
    @TableId
    private Integer id;
    private Integer cid;
    private Integer fid;
    private Integer count;
    private Double total;


    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    @TableField(exist = false)
    private Customer customer;

    @TableField(exist = false)
    private Food food;

    private Integer status;
    private Integer isorder;
    @TableField(exist = false)
    private String totalMoney;
    @TableField(exist = false)
    private String foodId;

}
