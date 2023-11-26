package com.itsystem.springbootorderfood.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.config
 * @className: MyMetaObjectHandler
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/13 11:38
 * @version: 1.0
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("orderTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("returnTime",new Date(),metaObject);
    }
}
