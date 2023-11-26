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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: NormalUserController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/12 18:37
 * @version: 1.0
 */
@Controller
@RequestMapping("/normalUser")
public class NormalUserController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private FoodService foodService;

    @RequestMapping("/listFood")
    public String listFood(
            @RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", required=false, defaultValue="12") Integer pageSize,
            Model model,
            Food food
    ){
        if(pageNum<=0 || pageNum.equals("") || pageNum==null){
            pageNum=1;
        }
        if(pageSize<=0 || pageSize.equals("") || pageSize==null){
            pageSize=12;
        }
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<Food> qw = new QueryWrapper<>();
        String foodName = food.getFoodName();
        if(foodName!=null){
            qw.like("food_name",foodName);
        }
        List<Food> list = foodService.list(qw);
        PageInfo<Food> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "customer-food-list";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 加购
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/foodToCart/{foodId}")
    public String foodToCart(
            @PathVariable("foodId") Integer foodId,HttpSession session
    ){
        Integer userId = (Integer) session.getAttribute("userId");
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("fid",foodId).eq("cid",userId).eq("isorder",0);
        List<Order> list = orderService.list(qw);
        System.out.println(list.isEmpty());
        if(list.isEmpty()){
            Order order = new Order();
            Food food = foodService.getById(foodId);
            order.setFid(foodId);
            order.setCid(userId);
            order.setCount(1);
            order.setIsorder(0);
            order.setTotal(food.getPrice());
            orderService.save(order);
            session.removeAttribute("msg");
            return "redirect:/normalUser/cart";
        }else{
            session.setAttribute("msg","This food is already in your cart!");
            return "redirect:/normalUser/listFood";
        }

    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 商详页浏览，获取该食物所在店铺的商品
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/toFoodSingle/{foodId}")
    public String toFoodSingle(
            @PathVariable("foodId") Integer foodId,Model model
    ){
        Food food = foodService.getById(foodId);
        model.addAttribute("food",food);
        QueryWrapper<Food> qw = new QueryWrapper<>();
        qw.eq("store", food.getStore());
        List<Food> list = foodService.list(qw);
        model.addAttribute("foodList", list);
        return "food-single";
    }
    /**
     * @return String
     * @author jiayueyuanfang
     * @description 收藏页面回显
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/collection")
    public String collection(
            @RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", required=false, defaultValue="12") Integer pageSize,
            Model model,
            Food food,
            HttpSession session
    ){
        if(pageNum<=0 || pageNum.equals("") || pageNum==null){
            pageNum=1;
        }
        if(pageSize<=0 || pageSize.equals("") || pageSize==null){
            pageSize=12;
        }
        Integer userId = (Integer) session.getAttribute("userId");
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = orderService.getCollection(userId);
        model.addAttribute("pageInfo",pageInfo);
        return "myCollection";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 收藏
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/foodToCollection/{foodId}")
    public String collection(
            @PathVariable("foodId") Integer foodId,
            HttpSession session,
            Model model
    ){
        Integer userId = (Integer) session.getAttribute("userId");
        QueryWrapper<Order> qw = new QueryWrapper<>();
        qw.eq("fid",foodId).eq("cid",userId).eq("isorder",2);
        Order one = orderService.getOne(qw);
        if(one!=null){
            session.setAttribute("msg", "This food has already been added to your collection!");
            return "redirect:/normalUser/listFood";
        }
        Food food = foodService.getById(foodId);
        Order order = new Order();
        order.setCid(userId);
        order.setFid(foodId);
        order.setCount(1);
        order.setIsorder(2);
        order.setTotal(food.getPrice());
        orderService.save(order);
        session.removeAttribute("msg");
        QueryWrapper<Order> qw2 = new QueryWrapper<Order>();
        qw2.eq("cid",userId).eq("isorder", 2);
        List<Order> list = orderService.list(qw2);
        PageInfo<Order> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "redirect:/normalUser/collection";
    }


    /**
     * @return String
     * @author jiayueyuanfang
     * @description 取消收藏
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/delCollection/{orderId}")
    public String delCollection(
            @PathVariable("orderId") Integer orderId
    ){
        boolean b = orderService.removeById(orderId);
        return "redirect:/normalUser/collection";
    }
    /**
     * @return String
     * @author jiayueyuanfang
     * @description 购物车页面回显
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/cart")
    public String cart(@RequestParam(value="pageNum", required=false, defaultValue="1") Integer pageNum,
                       @RequestParam(value="pageSize", required=false, defaultValue="12") Integer pageSize,
                       Model model,
                       HttpSession session){
        if(pageNum<=0 || pageNum.equals("") || pageNum==null){
            pageNum=1;
        }
        if(pageSize<=0 || pageSize.equals("") || pageSize==null){
            pageSize=12;
        }
        Integer userId = (Integer) session.getAttribute("userId");
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Order> pageInfo = orderService.getCart(userId);

        model.addAttribute("pageInfo",pageInfo);
        List<Order> list = pageInfo.getList();
        Double totalMoney = 0.0;
        for(Order order : list){
            totalMoney += order.getTotal() * order.getCount();
        }
        model.addAttribute("totalMoney",totalMoney);
        return "cart";
    }
    /**
     * @return String
     * @author jiayueyuanfang
     * @description 购物车的支付功能
     * @date 2023/11/13 11:14
     */

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 购物车的删除功能
     * @date 2023/11/13 11:14
     */
    @RequestMapping("payOrder")
    public String payOrder(HttpSession session)
    {
        Integer userId = (Integer) session.getAttribute("userId");
        boolean b = orderService.payOrder(userId);
        return "redirect:/normalUser/myOrders";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 我的订单的回显功能
     * @date 2023/11/13 11:14
     */
    @RequestMapping("myOrders")
    public String myOrders(@RequestParam(required = false,value = "pageNum",defaultValue = "1")Integer pageNum,
                           @RequestParam(required = false,value = "pageSize",defaultValue = "12")Integer pageSize, Model model, HttpSession session,Food food){
        if (pageNum<0 || pageNum==null || pageNum.equals("")){
            pageNum=1;
        }
        if (pageSize<0 || pageSize==null || pageSize.equals("")){
            pageSize=12;
        }
        PageHelper.startPage(pageNum,pageSize);
        Integer userId = (Integer) session.getAttribute("userId");
        String foodName = food.getFoodName();
        PageInfo<Order>pageInfo=orderService.getOrderList(userId,foodName);
        model.addAttribute("pageInfo",pageInfo);
        return "myOrders-list";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 我的订单的修改页面回显
     * @date 2023/11/13 11:14
     */
    @RequestMapping("preUpdateMyOrder/{orderId}")
    public String preUpdateMyOrder(@PathVariable("orderId") Integer orderId,Model model){
        Order order = orderService.getById(orderId);
//        System.out.println("attetion"+order.getFid());
        Integer cid = order.getCid();
        Integer fid = order.getFid();
        Food food = foodService.getById(fid);
        Customer customer = customerService.getById(cid);
//        System.out.println("attention"+food.getFoodName());
        model.addAttribute("order",order);
        model.addAttribute("food",food);
        model.addAttribute("customer",customer);
        return "myOrders-update";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 我的订单的修改订单功能
     * @date 2023/11/13 11:14
     */
    @RequestMapping("updateMyOrder")
    public String updateMyOrder(Integer id,Integer count){
        Order order = orderService.getById(id);
        Double total = order.getTotal();
        Integer count1 = order.getCount();
        Integer fid = order.getFid();
        Food food = foodService.getById(fid);
        order.setCount(count);

        Double total2 = total+(count-count1)*food.getPrice();
        System.out.println("attetion" +total2);
        order.setTotal(total2);

        food.setStock(food.getStock()-(count-count1));
        orderService.updateById(order);
        foodService.updateById(food);
        return "redirect:/normalUser/myOrders";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 我的订单的确认收货功能
     * @date 2023/11/13 11:14
     */
    @RequestMapping("confirmOrder/{orderId}")
    public String confirmOrder(@PathVariable("orderId") Integer orderId){
        Order order = orderService.getOrderById(orderId);
        order.setStatus(2);
        orderService.updateById(order);
        return "redirect:/normalUser/myOrders";
    }

    /**
     * @return String
     * @author jiayueyuanfang
     * @description 我的订单的打印发票功能
     * @date 2023/11/13 11:14
     */
    @RequestMapping("/invoice/{orderId}")
    public String invoice(@PathVariable("orderId") Integer orderId,Model model){
        Order order = orderService.getInvoiceById(orderId);
        model.addAttribute("order",order);
        return "invoice";
    }

    @RequestMapping("/batchDeleteMyOrder")
    @ResponseBody
    public String batchDeleteMyOrder(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer> list = new ArrayList<>();
        for(String s : split){
            if(!s.isEmpty()){
                list.add(Integer.valueOf(s));
            }
        }
        boolean b = orderService.removeByIds(list);
        if(b){return "OK";}
        else{return "ERROR";}
    }
}