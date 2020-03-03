package com.lzy.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5密码加密的工具类
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public class MD5Util {
	/**
	 * MD5加密
	 * @param src 用户发送的密码
	 * @return MD5加密后的密码
	 */
	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}
	
	private static final String salt = "darksouls_lzy";
	
	public static String inputPassToFormPass(String inputPass) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	public static String formPassToDBPass(String formPass, String salt) {
		String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}
	
	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}
}
