package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.ShopDao;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    ShopDao shopDao;

    public Shop getByUserId(User user){
        return shopDao.getShopByUserId(user);
    }

}
