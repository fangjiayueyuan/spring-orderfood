package com.itsystem.springbootorderfood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itsystem.springbootorderfood.mapper.StoreMapper;
import com.itsystem.springbootorderfood.pojo.Store;
import com.itsystem.springbootorderfood.service.StoreService;
import org.springframework.stereotype.Service;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.service.impl
 * @className: StoreServiceImpl
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/11/1 20:52
 * @version: 1.0
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
}
