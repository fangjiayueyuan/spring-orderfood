package com.itsystem.springbootorderfood;

import com.itsystem.springbootorderfood.controller.OrderController;
import com.itsystem.springbootorderfood.mapper.OrderMapper;
import com.itsystem.springbootorderfood.pojo.CountNumber;
import com.itsystem.springbootorderfood.pojo.Customer;
import com.itsystem.springbootorderfood.pojo.MainMenu;
import com.itsystem.springbootorderfood.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class SpringbootOrderfoodApplicationTests {
//	@Autowired
//	private OrderMapper orderMapper;
//
//	@Test
//	void contextLoads() {
//		List<Order> orders = orderMapper.queryAll(null);
//		for(Order order : orders){
//			System.out.println(order);
//		}
//	}

//	@Autowired
//	private OrderController orderController;
//	@Test
//	void contextLoads() {
////		String orderId = "123";
//		Model model = new ExtendedModelMap();
//		String result = orderController.preUpdateOrder(4, model);
//		System.out.println(result);
//	}

//	@Autowired
//	private OrderController orderController;
//	@Test
//	void contextLoads() {
//		orderController.sendMessage("miye_0628@qq.com","牛奶",189);
//
//	}
//	@Test
//	void contextLoads() {
////		String originalFilename = file.getOriginalFilename();
//		int index = "example.jpg".lastIndexOf(".");
//		System.out.println("index" + index);
//
//	}

	@Autowired
	private OrderMapper orderMapper;
	@Test
	void contextLoads(){
		List<CountNumber> mainMenus = orderMapper.queryCount();
		for(CountNumber mainMenu : mainMenus){
			System.out.println(mainMenu.getName());
			System.out.println(mainMenu.getCount());
		}
	}
}