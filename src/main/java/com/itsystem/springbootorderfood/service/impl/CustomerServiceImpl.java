package com.itsystem.springbootorderfood.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsystem.springbootorderfood.mapper.AccountMapper;
import com.itsystem.springbootorderfood.mapper.CustomerMapper;
import com.itsystem.springbootorderfood.pojo.Customer;
import com.itsystem.springbootorderfood.pojo.User;
import com.itsystem.springbootorderfood.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.service.impl
 * @className: CustomerServiceImpl
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/10/30 15:13
 * @version: 1.0
 */

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public boolean login(String userName, String userPwd) {
        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("customer_name",userName);
        Customer customer = customerMapper.selectOne(qw);
        if(customer==null){
            return false;
        }
        String s = DigestUtil.md5Hex(userPwd);
        if(s.equals(customer.getPassword())){
            return true;
        }
        return false;
    }
}
