package com.lzy.miaosha.redis;
/**
 * @Description 实现KeyPrefix的虚拟类，redis数据库的key分类
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
public abstract class BasePrefix implements KeyPrefix{
	
	private int expireSeconds;
	
	private String prefix;
	
	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}
	
	public BasePrefix(int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}
	@Override
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}
	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
