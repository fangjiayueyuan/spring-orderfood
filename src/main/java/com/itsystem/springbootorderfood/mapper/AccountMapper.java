package com.itsystem.springbootorderfood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itsystem.springbootorderfood.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper extends BaseMapper<User> {
}
