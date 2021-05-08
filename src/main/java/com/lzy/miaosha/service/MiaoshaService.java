package com.lzy.miaosha.service;

import com.lzy.miaosha.dao.GoodsDao;
import com.lzy.miaosha.domain.*;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.MiaoshaKey;
import com.lzy.miaosha.util.MD5Util;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.GoodsVo;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class MiaoshaService {

    @Autowired
    RedisService redisService;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsDao goodsDao;

    /**
     * 检查验证码是否正确
     * @param user 用户信息
     * @param goodsId 商品id
     * @param verifyCode 验证码
     * @return true 正确 false 错误
     */
    public boolean checkVerifyCode(User user,String goodsId,String verifyCode){
        if(user == null || goodsId == null){
            return false;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(user.getPhonenumber());
        sb.append(",");
        sb.append(goodsId);
        String key = sb.toString();
        String code = redisService.get(MiaoshaKey.getMiaoshaVerifyCode,key,String.class);
        code = code.toLowerCase();
        verifyCode = verifyCode.toLowerCase();
        if(code == null||!code.equals(verifyCode)){
            return false;
        }
        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode,key);
        return true;
    }

    public String createMiaoshaPath(User user, String goodsId) {
        if(user == null || goodsId == null){
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(MiaoshaKey.getMiaoshaPath,user.getId()+"_"+goodsId,str);
        return str;
    }

    /**
     * 检查隐藏秒杀url中的path部分
     * @param user 用户信息
     * @param goodsId 商品id
     * @param path 动态生成的部分地址
     * @return true 验证成功
     */
    public boolean checkPath(User user, Integer goodsId, String path) {
        if(user == null || path == null || goodsId == null) {
            return false;
        }
        String dbPath = redisService.get(MiaoshaKey.getMiaoshaPath,user.getId()+"_"+goodsId,String.class);
        return path.equals(dbPath);
    }
    //生成订单
    public OrderInfo miaosha(User user, GoodsVo goods,Integer addressId) {
        OrderInfo order = new OrderInfo();
        order.setId(UUIDUtil.uuid());
        java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        order.setPayDate(date);
        order.setCreateDate(date);
        order.setUserId(user.getId());
        order.setStatus(1);
        order.setGoodsPrice(goods.getMiaoshaPrice());
        order.setGoodsCount(1);
        order.setGoodsName(goods.getGoodsName());
        order.setDeliveryAddrId(addressId);
        order.setGoodsId(goods.getId());
        return order;
    }

    /**
     * 获得秒杀的结果
     * @param userId 用户id
     * @param goodsId 商品ID
     * @return
     */
    public Long getMiaoshaResult(Integer userId, Integer goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null){
            return order.getId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1L;
            }else {
                return 0L;
            }
        }
    }

    private boolean getGoodsOver(Integer goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,goodsId.toString());
    }

    public void insertMiaoShaGoods(GoodsVo goodsVo, Goods goods) {
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goods.getId());
        miaoshaGoods.setStockCount(goodsVo.getStockCount());
        miaoshaGoods.setStartDate(new java.sql.Date(goodsVo.getStartDate().getTime()));
        miaoshaGoods.setMiaoshaPrice(goodsVo.getMiaoshaPrice());
        miaoshaGoods.setEndDate(new java.sql.Date(goodsVo.getEndDate().getTime()));
        goodsDao.insertMiaoShaGoods(miaoshaGoods);
    }

    public void createMiaoshaOrder(User user,Goods goods,OrderInfo orderInfo){
        goodsDao.createMiaoshaOrder(user.getId(),goods.getId(),orderInfo.getId());
    }

    public MiaoshaOrder getMiaoshaOrderByOrderId(String orderId){
        return goodsDao.getMiaoshaOrderByOrderId(orderId);
    }

}
