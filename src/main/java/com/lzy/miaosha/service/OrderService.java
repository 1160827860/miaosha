package com.lzy.miaosha.service;

import com.lzy.miaosha.domain.MiaoshaOrder;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.OrderKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务
 */
@Service
public class OrderService {

    @Autowired
    RedisService redisService;

    /**
     * 查找用户是不是已经购买了商品
     * @param userId 用户id
     * @param goodsId 商品id flowsnow算法所以用String
     * @return 秒杀订单的信息
     */
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Integer userId, Integer goodsId) {
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId, MiaoshaOrder.class);
    }
}
