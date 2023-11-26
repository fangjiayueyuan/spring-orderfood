package com.itsystem.springbootorderfood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsystem.springbootorderfood.pojo.CountNumber;
import com.itsystem.springbootorderfood.pojo.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
    List<Order> queryAll(Integer id);

    Order getOrderById(Integer orderId);

    List<CountNumber> queryCount();

    List<CountNumber> queryTotal();

    List<Order> getCollection(Integer userId);

    List<Order> getCart(Integer userId);

    List<Order> getOrderList(Integer userId, String foodName);

    boolean payOrder(Integer userId);

    Order getInvoiceById(Integer orderId);

//    Order getAllByCidOrder(Integer orderId);
}
