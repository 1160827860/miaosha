package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.UserDao;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class RootService {
    private static Logger log = LoggerFactory.getLogger(RootService.class);

    @Autowired
    UserDao userDao;


    /**
     * 封号,并且把封号人的信息写入redis中禁止登录
     * @param phone
     */
    public void  banUser(String phone){
       User user = new User();
       user.setAuthority("3");
       user.setPhonenumber(phone);
       userDao.updateUser(user);
    }

    public List<Shop> getAllShopApply(){
        return userDao.getAllShop();
    }

    /**
     * 审核用户提交的开店申请
     * @param userId
     * @param shopId
     */
    public void  agreeAplly(int userId,int shopId){
        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setAuthority("2");
        User user = new User();
        user.setAuthority("2");
        user.setId(userId);
        userDao.updateUserById(user);
        userDao.updateShopById(shop);
    }

    /**
     * 封禁店铺
     * @param id 店铺id
     */
    public void banShop(int id){
        Shop shop = new Shop();
        shop.setId(id);
        shop.setAuthority("3");
        userDao.updateShopById(shop);
    }
}
