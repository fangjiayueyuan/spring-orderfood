package com.itsystem.springbootorderfood.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.pojo.Customer;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.pojo.Order;
import com.itsystem.springbootorderfood.service.CustomerService;
import com.itsystem.springbootorderfood.service.FoodService;
import com.itsystem.springbootorderfood.service.OrderService;
import com.itsystem.springbootorderfood.utis.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: OrderController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/4 17:11
 * @version: 1.0
 */
@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/listOrder")
    public String listOrder(
        @RequestParam(name = "pageNum",defaultValue = "1",required = false) Integer pageNum,
        @RequestParam(name = "pageSize", defaultValue = "10",required = false) Integer pageSize,
        Model model,
        Order order
    ){
        if(pageNum == null || pageNum.equals("") || pageNum<0){
            pageNum = 1;
        }
        if(pageSize == null || pageSize.equals("") || pageSize<0){
            pageSize = 10;
        }

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = orderService.queryAll(order.getId()); // 根据订单id查询
        model.addAttribute("pageInfo",pageInfo);
        return "order-list";
    }

    @RequestMapping("/preUpdateOrder/{orderId}")
    public String preUpdateOrder(@PathVariable("orderId")Integer orderId, Model model ) {
        Order order = orderService.getOrderById(orderId);
        List<Food> foodList = foodService.list(null);
//        System.out.println("foodList"+foodList);
        List<Customer> customerList = customerService.list(null);
//        System.out.println("customerList"+customerList);
        model.addAttribute("order",order);
        model.addAttribute("foodList",foodList);
        model.addAttribute("customerList",customerList);
//        System.out.println(model);
        return "order-update";
    }

    @RequestMapping("/updateOrder")
    public String updateOrder(Integer cid,Integer fid,Integer count){
        QueryWrapper<Order> qw = new QueryWrapper<Order>();
        qw.eq("cid",cid);
        qw.eq("fid",fid);
        Order order = orderService.getOne(qw);
        Integer oldCount = order.getCount();
        order.setCount(count);
//        order.getFood()
//        order.setTotal(order.getFood().getPrice() * count);
        order.setTotal(foodService.getById(fid).getPrice()*count);
        orderService.updateById(order);
        // 修改库存
        Food food = foodService.getById(fid);
        food.setStock(
                food.getStock() - (count - oldCount)
        );
        foodService.updateById(food);
        return "redirect:/order/listOrder";
    }

    @RequestMapping("/delOrder/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Integer orderId) {
        boolean result = orderService.removeById(orderId);
        return "redirect:/order/listOrder";
    }

    @RequestMapping("/batchDeleteOrder")
    @ResponseBody
    public String batchDeleteOrder(String idList){
        String[] split = StrUtil.split(idList, ",");
        ArrayList<Integer> list = new ArrayList<>();
        for(String s : split){
            if(!s.isEmpty()){
                Integer integer = Integer.valueOf(s);
                list.add(integer);
            }

        }
        boolean b = orderService.removeByIds(list);
        if(b){
            return "OK";
        }else{
            return "ERROR";
        }
    }


    @RequestMapping("sendMessage/{email}/{foodName}/{orderId}")
    public String sendMessage(@PathVariable String email,@PathVariable String foodName,@PathVariable Integer orderId){
        MailUtils.sendMail(email, "您订购的 " + foodName + " 正在配送中，订单号为" + orderId, "小圆外卖");
        Order order = orderService.getById(orderId);
        order.setStatus(1);
        orderService.updateById(order);
        return "redirect:/order/listOrder";
    }
}
