package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.GoodsDao;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> getGoodsList(){
        return goodsDao.listGoods();
    }

    public GoodsVo getGoodsVoByGoodsId(String goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }
}
