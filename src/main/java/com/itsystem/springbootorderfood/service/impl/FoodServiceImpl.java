package com.itsystem.springbootorderfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsystem.springbootorderfood.mapper.FoodMapper;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.service.FoodService;
import org.springframework.stereotype.Service;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.service.impl
 * @className: FoodServiceImpl
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 16:40
 * @version: 1.0
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
}
