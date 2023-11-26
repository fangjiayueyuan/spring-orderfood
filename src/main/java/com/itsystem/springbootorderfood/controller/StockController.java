package com.itsystem.springbootorderfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: StockController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/8 17:27
 * @version: 1.0
 */
@Controller
@RequestMapping("stock")
public class StockController {
    @Autowired
    private FoodService foodService;
    @RequestMapping("/listStock")
    public String listStock(
            @RequestParam(value="pageNum",defaultValue = "1",required = false) Integer pageNum,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) Integer pageSize,
            Model model,
            String foodName
//            Food food
    ){
        if(pageNum<0 || pageNum.equals("") || pageNum==null){
            pageNum = 1;
        }
        if(pageSize<0 || pageSize.equals("") || pageSize==null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Food> qw = new QueryWrapper<>();
        if(foodName != null){
            qw.like("food_name",foodName);
        }
        List<Food> list = foodService.list(qw);
        PageInfo<Food> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "stock-list";
    }

//    http://localhost:8080/stock/addStock/23
    @RequestMapping("/addStock/{foodId}")
    public String addStock(@PathVariable("foodId") Integer foodId, Model model){
        Food one = foodService.getById(foodId);
        one.setStock(one.getStock() + 100);
        foodService.updateById(one);
        return "redirect:/stock/listStock"; // 注意：重定向是后端地址

    }
}
