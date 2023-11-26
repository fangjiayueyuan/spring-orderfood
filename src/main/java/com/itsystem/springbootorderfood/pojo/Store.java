package com.itsystem.springbootorderfood.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.pojo
 * @className: Store
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 20:46
 * @version: 1.0
 */
@Data
@TableName("store")
public class Store {
    private Integer id;
    private String storeName;
    private String descr;
}
