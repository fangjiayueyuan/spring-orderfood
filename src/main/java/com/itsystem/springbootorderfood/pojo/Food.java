package com.itsystem.springbootorderfood.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.pojo
 * @className: Food
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 16:35
 * @version: 1.0
 */
@Data
@TableName("food")
public class Food {
    private Integer id;
    private String foodName;
    private String store;
    private Double price;
    private Integer stock;// 库存
    private String descr;
    private String fimage;
    private Integer status;
}
