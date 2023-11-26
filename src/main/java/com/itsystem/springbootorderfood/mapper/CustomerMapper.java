package com.itsystem.springbootorderfood.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsystem.springbootorderfood.pojo.Customer;
import org.springframework.stereotype.Repository;

@Repository // 表示是mapper层
public interface CustomerMapper extends BaseMapper<Customer> {
}
