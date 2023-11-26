package com.itsystem.springbootorderfood.controller;

import com.itsystem.springbootorderfood.pojo.CountNumber;
import com.itsystem.springbootorderfood.pojo.MainMenu;
import com.itsystem.springbootorderfood.pojo.MainMenu2;
import com.itsystem.springbootorderfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: MainMenuController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/7 21:57
 * @version: 1.0
 */
@RestController
@RequestMapping("main")
public class MainMenuController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/mainMenu")
    public List<MainMenu> queryCount(){
       List<CountNumber> list =  orderService.queryCount();
       List<MainMenu> menus = new ArrayList<>();
       int viewCount = 0; // 前端展示的数量是12个，通过viewCount做下统计
       for(CountNumber countNumber : list){
           if(viewCount <= 12){
               MainMenu mainMenu = new MainMenu();
               mainMenu.setType(countNumber.getName());
               mainMenu.setMount(Integer.valueOf(countNumber.getCount()));
               menus.add(mainMenu);
               viewCount++;
           }else {
               break;
           }
       }
       return menus;
    }

    @RequestMapping("/mainMenu2")
    public List<MainMenu2> queryTotal(){
        List<CountNumber> list = orderService.queryTotal();
        int num = 0;
        List<MainMenu2> menu2 = new ArrayList<>();
        for(CountNumber countNumber:list){
            if(num<12){
                MainMenu2 mainMenu2 = new MainMenu2();
                mainMenu2.setType(countNumber.getName());
                mainMenu2.setMount(Double.valueOf(countNumber.getCount()));
                menu2.add(mainMenu2);
                num++;
            }else{
                break;
            }
        }
        return menu2;
    }
}
