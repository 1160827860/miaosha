package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;

/**
 * @Description 保存在redis中的验证码的key值中的一部分
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public class CheckKey extends BasePrefix {
    /**
     * 保存秒杀界面的验证码
     * 不是登陆界面的，登陆界面用了session
     */
    public static CheckKey getCkCo = new CheckKey(300,"ck");

    public CheckKey(String prefix) {
        super(prefix);
    }

    public CheckKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
