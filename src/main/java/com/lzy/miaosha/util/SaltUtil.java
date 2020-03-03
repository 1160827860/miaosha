package com.lzy.miaosha.util;

import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

/**
 * 随机生成用于加密密码的字符串
 * @author 李正阳
 * @version 0.1
 * @date 2020/2/27
 */
public class SaltUtil {
    private static String randString = "0123456789abcdefghijkmnpqrtyABCDEFGHIJLMNQRTY";//随机生成字符串的取值范围

    public static String getSalt(){
        StringBuffer sb = new StringBuffer();
        int n = RandomUtils.nextInt(6,9);
        for (int i = 0; i < n; i++) {
            sb.append(randString.charAt(RandomUtils.nextInt(0,44)));
        }
        return sb.toString();
    }

}
