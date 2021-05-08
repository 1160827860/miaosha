package com.lzy.miaosha.redis.keys;

import com.lzy.miaosha.redis.BasePrefix;
import com.lzy.miaosha.redis.KeyPrefix;

/**
 * @Description 存储用户信息的key
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public class UserKey extends BasePrefix {
	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	public static UserKey getById = new UserKey(0,"id");
	public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
	public static UserKey getByPh = new UserKey(TOKEN_EXPIRE,"ph");
	/**
	 * 防止缓存穿透
	 */
	public static UserKey getByPhNull = new UserKey(10,"ph");
	public UserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	private UserKey(String prefix) {
		super(prefix);
	}


}
