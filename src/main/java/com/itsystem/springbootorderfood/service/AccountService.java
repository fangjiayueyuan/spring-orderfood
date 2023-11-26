package com.itsystem.springbootorderfood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itsystem.springbootorderfood.pojo.User;

public interface AccountService extends IService<User> {
    boolean login(String userName, String userPwd);
}
