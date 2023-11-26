package com.itsystem.springbootorderfood;

import com.itsystem.springbootorderfood.mapper.OrderMapper;
import com.itsystem.springbootorderfood.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootOrderfoodApplicationTests1 {
	@Autowired
	private OrderMapper orderMapper;

	@Test
	void contextLoads() {
		Order orderById = orderMapper.getOrderById(4);
		System.out.println(orderById );
	}

}
