package com.itsystem.springbootorderfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsystem.springbootorderfood.pojo.Customer;

public interface CustomerService extends IService<Customer> {
    boolean login(String userName, String userPwd);
}
