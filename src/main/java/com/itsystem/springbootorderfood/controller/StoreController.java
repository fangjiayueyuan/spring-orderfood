package com.itsystem.springbootorderfood.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.pojo.Store;
import com.itsystem.springbootorderfood.service.FoodService;
import com.itsystem.springbootorderfood.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @className: StoreController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 21:00
 * @version: 1.0
 */
@Controller
@RequestMapping("store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private FoodService foodService;
    @RequestMapping("listStore")
    public String listStore(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            Model model,
            Store store
    ) {
        if(pageNum<0 || pageNum.equals("") || pageNum==null){
            pageNum = 1;
        }
        if(pageSize<0 || pageSize.equals("") || pageSize==null){
            pageSize = 10 ;
        }
        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper<Store> qw = new QueryWrapper<>();
        if(store.getStoreName() != null){
            qw.like("store_name",store.getStoreName());
        }
        List<Store> listService = storeService.list(qw);
        PageInfo<Store> list = new PageInfo<>(listService);
        model.addAttribute("pageInfo", list);
        return "store-list";
    }

    @RequestMapping("preSaveStore")
    public String preSaveStore(){
        return "store-save";
    }
    @RequestMapping("saveStore")
    public String saveStore(Store store,Model model){
        storeService.save(store);
        model.addAttribute(store);
        return "redirect:/store/listStore";
    }

    @RequestMapping("preUpdateStore/{storeId}")
    public String preUpdateStore(@PathVariable("storeId") Integer storeId,Model model){
        Store one = storeService.getById(storeId);
        model.addAttribute("store", one);
        return "store-update";
    }
    @RequestMapping("updateStore")
    public String updateStore(Model model,Store store){
        boolean b = storeService.updateById(store);
        return "redirect:/store/listStore";
    }
    @RequestMapping("delStore/{storeId}")
    public String updateStore(@PathVariable("storeId") Integer storeId,Model model,Store store){
        boolean b = storeService.removeById(storeId);
        return "redirect:/store/listStore";
    }

    @RequestMapping("batchDeleteStore")
    @ResponseBody
    public String batchDeleteStore(String idList){
        String[] split = StrUtil.split(idList, ",");
        List<Integer> list = new ArrayList<>();
        for(String s : split){
            if(!s.isEmpty()){
                list.add(Integer.parseInt(s));
            }
        }
        boolean b = storeService.removeByIds(list);
        if(b){
            return "OK";
        }else {
            return "ERROR";
        }
    }

    @RequestMapping("storeList/{name}")
    public String batchDeleteStore(
            @PathVariable("name") String name,
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            Model model
    ){
        if(pageNum<0 || pageNum.equals("") || pageNum==null){
            pageNum = 1;
        }
        if(pageSize<0 || pageSize.equals("") || pageSize==null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Food> qw = new QueryWrapper<>();
        if(!name.isEmpty()){
            qw.eq("store",name);
        }
        List<Food> list = foodService.list(qw);
        PageInfo<Food> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        return "store-food-list";
    }
}
