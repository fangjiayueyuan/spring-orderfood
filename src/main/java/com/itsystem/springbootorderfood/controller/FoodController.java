package com.itsystem.springbootorderfood.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.pojo.Store;
import com.itsystem.springbootorderfood.service.FoodService;
//import net.sf.jsqlparser.Model;
import com.itsystem.springbootorderfood.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: FoodController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 17:04
 * @version: 1.0
 */
@Controller
@RequestMapping("food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private StoreService storeService;

    @Value("${location}")
    private String location;

    @RequestMapping("listFood")
    public String listFood(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                           @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize, Model model
                           , Food food
                           ,Store store
                           ){
        if(pageNum==null || pageNum.equals("")|| pageNum<0){
            pageNum=1;
        }
        if(pageSize==null || pageSize.equals("")|| pageSize<0){
            pageNum=10;
        }
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Food> qw = new QueryWrapper<>(); // 条件构造器
        if(!(food.getFoodName()==null)){
            qw.like("food_name",food.getFoodName()); // 模糊查询
        }
        if(!(food.getStore()==null)){
            qw.like("store",food.getStore()); // 模糊查询
        }

        List<Food> list = foodService.list(qw); // 根据条件构造器构造的条件来查询满足条件的对象，并返回成List
        PageInfo<Food> pageInfo = new PageInfo<>(list); // 封装到插件中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("food", food);

//        QueryWrapper<Store> qwStore = new QueryWrapper<>(); // 条件构造器-- 此处不需要条件构造，因为前端不需要过滤
//        if(!(store.getStoreName()==null)){
//            qwStore.like("store_name",store.getStoreName());
//        }

        List<Store> storeList = storeService.list(null);// 不过滤获取全部商店
        PageInfo<Store> pageInfoStore = new PageInfo<>(storeList); // 封装到插件中
        model.addAttribute("storeList", storeList);
        return "food-list";
    }
    @RequestMapping("preSaveFood")
    public String preSaveFood(Model model) {
        List<Store> list = storeService.list(null);
        model.addAttribute("storeList",list);
        return "food-save";
    }
    
    @RequestMapping("saveFood")
    public String saveFood(Food food, MultipartFile file) throws IOException {
        transFile(food,file);
        boolean save = foodService.save(food);
        return "redirect:/food/listFood";  // 重定向，因为数据库数据变了
    }

    private void transFile(Food food, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String prefix = System.nanoTime()+"";
        String path = prefix + suffix;
        File file1 = new File(location);
        if(!file1.exists()){
            file1.mkdir();
        }
        File file2 = new File(file1, path);
        file.transferTo(file2);
        food.setFimage(path);
    }
    @RequestMapping("preUpdateFood/{foodId}")
    public String preUpdateFood(@PathVariable Integer foodId, Model model){
        List<Store> list = storeService.list(null);
        model.addAttribute("storeList",list);
        Food food = foodService.getById(foodId);
        model.addAttribute("food",food);
        return "food-update";
    }
    @RequestMapping("updateFood")
    public String updateFood(Food food) {
        foodService.updateById(food);
        return "redirect:/food/listFood";  // 重定向，因为数据库数据变了
    }

    @RequestMapping("delFood/{foodId}")
    public String delFood(@PathVariable Integer foodId) {
        foodService.removeById(foodId);
        return "redirect:/food/listFood";  // 重定向，因为数据库数据变了
    }
    @RequestMapping("batchDeleteFood")
    @ResponseBody
    public String batchDeleteFood(String idList){
        String[] split = StrUtil.split(idList,",");
        List<Integer> list = new ArrayList<>();
        for(String s : split){
            if(!s.isEmpty()){
                Integer integer = Integer.valueOf(s);
                list.add(integer);
            }
        }
        boolean b = foodService.removeByIds(list);
        if(b){
            return "OK";
        }else{
            return "error";
        }
    }

    @RequestMapping("shangjia/{foodId}")
    public String shangjia(@PathVariable Integer foodId){
        Food food = foodService.getById(foodId);
        food.setStatus(1);
        boolean b = foodService.updateById(food);
        return "redirect:/food/listFood";
    }

    @RequestMapping("xiajia/{foodId}")
    public String xiajia(@PathVariable Integer foodId){
        Food food = foodService.getById(foodId);
        food.setStatus(2);
        boolean b = foodService.updateById(food);
        return "redirect:/food/listFood";
    }


}
