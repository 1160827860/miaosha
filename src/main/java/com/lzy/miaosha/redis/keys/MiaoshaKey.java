package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;
import com.lzy.miaosha.redis.KeyPrefix;

public class MiaoshaKey extends BasePrefix {

    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
    public static KeyPrefix getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
