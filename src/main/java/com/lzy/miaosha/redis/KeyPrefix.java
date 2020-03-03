package com.lzy.miaosha.redis;

/**
 * @Description KeyPrefix的接口
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public interface KeyPrefix {
		
	 int expireSeconds();
	
	 String getPrefix();
	
}
