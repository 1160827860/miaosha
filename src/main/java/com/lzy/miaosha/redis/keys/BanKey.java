package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;

public class BanKey extends BasePrefix {

    public static BanKey banedUser = new BanKey(0,"Ban");

    public BanKey(String prefix) {
        super(prefix);
    }

    public BanKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
