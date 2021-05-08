package com.lzy.miaosha.dao;

import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ShopDao {
    @Select("select * from shop where user_id = #{id}")
    Shop getShopByUserId(User user);
}
