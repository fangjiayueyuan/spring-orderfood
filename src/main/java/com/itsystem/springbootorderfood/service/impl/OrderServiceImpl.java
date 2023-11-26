package com.itsystem.springbootorderfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.itsystem.springbootorderfood.mapper.OrderMapper;
import com.itsystem.springbootorderfood.pojo.CountNumber;
import com.itsystem.springbootorderfood.pojo.Food;
import com.itsystem.springbootorderfood.pojo.MainMenu;
import com.itsystem.springbootorderfood.pojo.Order;
import com.itsystem.springbootorderfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.service.impl
 * @className: OrderServiceImpl
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/4 17:06
 * @version: 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageInfo<Order> queryAll(Integer id) {
        List<Order> list =  orderMapper.queryAll(id);
        return new PageInfo<>(list);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public List<CountNumber> queryCount() {
        return orderMapper.queryCount();
    }

    @Override
    public List<CountNumber> queryTotal() {
        return orderMapper.queryTotal();
    }

    @Override
    public PageInfo<Order> getCollection(Integer userId) {
        List<Order> orders = orderMapper.getCollection(userId);
        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<Order> getCart(Integer userId) {
        List<Order> orders = orderMapper.getCart(userId);
        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<Order> getOrderList(Integer userId, String foodName) {
        List<Order> orders = orderMapper.getOrderList(userId,foodName);
        return new PageInfo<>(orders);
    }

    @Override
    public boolean payOrder(Integer userId) {
        boolean b = orderMapper.payOrder(userId);
        return b;
    }

    @Override
    public Order getInvoiceById(Integer orderId) {
        return orderMapper.getInvoiceById(orderId);
    }
}
