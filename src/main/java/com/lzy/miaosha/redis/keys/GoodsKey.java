package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;

/**
 * 关于商品信息的key
 */
public class GoodsKey extends BasePrefix {
    /**
     * 为了防止缓存穿透，所以设置一个10s的缓存
     */
    public static GoodsKey getGoodsNull = new GoodsKey(10,"gs");

    public static GoodsKey getMiaoShaGoods = new GoodsKey(0,"gs");

    public GoodsKey(String prefix) {
        super(prefix);
    }

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
