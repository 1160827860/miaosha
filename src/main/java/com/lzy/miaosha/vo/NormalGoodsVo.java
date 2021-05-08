package com.lzy.miaosha.vo;

import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.domain.User;

import java.util.List;

public class NormalGoodsVo {

    User user;

    Goods goods;

   List<AddressVo> addressVos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<AddressVo> getAddressVos() {
        return addressVos;
    }

    public void setAddressVos(List<AddressVo> addressVos) {
        this.addressVos = addressVos;
    }
}
