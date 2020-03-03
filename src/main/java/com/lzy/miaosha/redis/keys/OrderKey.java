package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;

public class OrderKey extends BasePrefix {
    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
