package com.itsystem.springbootorderfood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsystem.springbootorderfood.pojo.Food;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.mapper
 * @className: FoodMapper
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 16:37
 * @version: 1.0
 */
@Repository
public interface FoodMapper extends BaseMapper<Food> {
}
