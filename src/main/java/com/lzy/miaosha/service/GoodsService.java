package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.GoodsDao;
import com.lzy.miaosha.dao.UserDao;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.domain.OrderInfo;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.GoodsVo;
import com.lzy.miaosha.vo.InsertGoodsVo;
import com.lzy.miaosha.vo.OrderVo;
import com.lzy.miaosha.vo.ShopingCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    UserDao userDao;


    public List<GoodsVo> getGoodsList(){
        return goodsDao.listGoods();
    }

    public GoodsVo getGoodsVoByGoodsId(Integer goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 插入商品
     * @param goodsVo
     * @param user
     */
    public void insertGoods(Goods goodsVo,User user){
        Shop shop = userDao.getShopByUserId(user.getId());
        goodsVo.setShopId(shop.getId());
        goodsVo.setShopName(shop.getName());
        goodsDao.insertGoods(goodsVo);
    }

    /**
     * 显示商家目前店铺中的商品信息
     */
    public List<Goods> showMyGoods(Shop shop){
        return goodsDao.showMyGoods(shop);
    }

    public void updateGoods(Goods goods){
        goodsDao.updateGoods(goods);
    }

    public List<Goods> getAllGoods(){

        return goodsDao.getAllGoods();
    }

    public Goods getGoodsById(Integer id){
        return goodsDao.getGoodsById(id);
    }

    public void addGoodsToShopintCart(Integer goodsId,Integer addressId,Integer number,User user){
        goodsDao.insertGoodsToCart(user.getId(),goodsId,addressId,number);
    }

    public void buyGoods(OrderInfo orderInfo){
        goodsDao.createOrder(orderInfo);

    }

    public List<OrderVo> getAllOrderByUserId(Integer id){
        return goodsDao.getAllOrderByUserId(id);
    }

    public void  changeGoodsStaus(Integer status,String id){
        goodsDao.reviveGoods(status,id);
    }

    public List<ShopingCartVo> getCartById(Integer id){
        return goodsDao.getCartById(id);
    }

    public void  delShopingCartGoods(Integer id){
        goodsDao.delCartGoods(id);
    }

    public OrderInfo buy(User user,Goods goods,Integer addressId,Integer number){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(UUIDUtil.uuid());
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setDeliveryAddrId(addressId);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(number);
        orderInfo.setGoodsPrice(goods.getGoodsPrice());
        orderInfo.setStatus(1);
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        orderInfo.setCreateDate(date);
        orderInfo.setPayDate(date);
        return orderInfo;
    }

    public List<OrderVo> sellerGetAllOrder(Integer userId){
        Shop shop = userDao.getShopByUserId(userId);
        if(shop == null){
            return null;
        }
        return goodsDao.sellerGetAllOrderByShopId(shop.getId());
    }

    /**
     * 普通商品减少库存
     * @param goods
     * @param stock
     */
    public void decrGoodsCount(Goods goods,Integer stock){
        goodsDao.updateCount(goods.getId(),stock);
    }
    /**
     * 秒杀商品减少库存
     * @param goods
     * @param stock
     */
    public void decrMiaoshaGoodsCount(Goods goods,Long stock){
        goodsDao.updateMiaoshaCount(goods.getId(),stock);
    }

    public List<Goods> rootGetAllGoodsBy(){
       return goodsDao.getAllGoodsByRoot();
    }
}
