package com.itsystem.springbootorderfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.pojo.CountNumber;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.pojo.MainMenu;
import com.itsystem.springbootorderfood.pojo.Order;

import java.util.List;

public interface OrderService extends IService<Order> {


    PageInfo<Order> getCollection(Integer userId);

    PageInfo<Order> queryAll(Integer id);

    Order getOrderById(Integer orderId);

    List<CountNumber> queryCount();

    List<CountNumber> queryTotal();

    PageInfo<Order> getCart(Integer userId);

    PageInfo<Order> getOrderList(Integer userId,String foodName);

    boolean payOrder(Integer userId);

    Order getInvoiceById(Integer orderId);
}
