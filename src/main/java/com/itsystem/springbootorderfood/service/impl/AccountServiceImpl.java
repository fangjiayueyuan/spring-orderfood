package com.itsystem.springbootorderfood.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsystem.springbootorderfood.mapper.AccountMapper;
import com.itsystem.springbootorderfood.pojo.User;
import com.itsystem.springbootorderfood.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.service.impl
 * @className: AccountServiceImpl
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/10/30 11:43
 * @version: 1.0
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, User> implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public boolean login(String userName, String userPwd) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",userName);
        User user = accountMapper.selectOne(qw);
        if(user==null){
            return false;
        }
        String s = DigestUtil.md5Hex(userPwd);
        if(s.equals(user.getPassword())){
            return true;
        }
        return false;
    }
}
