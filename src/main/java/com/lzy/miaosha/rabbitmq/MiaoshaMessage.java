package com.lzy.miaosha.rabbitmq;

import com.lzy.miaosha.domain.User;

/**
 * rabbitMq发送的消息实体
 * @author 李正阳
 */
public class MiaoshaMessage {
	private User user;
	private String goodsId;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
}
